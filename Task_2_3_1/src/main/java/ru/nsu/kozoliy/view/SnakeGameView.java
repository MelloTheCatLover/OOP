package ru.nsu.kozoliy.view;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.kozoliy.models.Direction;
import ru.nsu.kozoliy.models.Settings;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.Snake;
import ru.nsu.kozoliy.models.SnakePart;
import ru.nsu.kozoliy.viewModel.SnakeGameViewModel;



/**
 * The view class responsible for displaying the Snake game.
 */
public class SnakeGameView extends Application {

    private final Model model;
    private final SnakeGameViewModel snakeGameViewModel;
    private GraphicsContext gc;
    public Timeline timeline = new Timeline();
    private Group foods = new Group();
    private Group snakeDraw = new Group();

    private Settings settings;

    public Group getFoods() {
        return foods;
    }

    /**
     * Constructs a SnakeGameView with the specified model, view model, and settings.
     *
     * @param model              The model of the game.
     * @param snakeGameViewModel The view model of the game.
     * @param settings           The settings of the game.
     */
    public SnakeGameView(Model model, SnakeGameViewModel snakeGameViewModel, Settings settings) {
        this.model = model;
        this.snakeGameViewModel = snakeGameViewModel;
        this.settings = settings;
    }

    /**
     * Gets the model of the game.
     *
     * @return The model of the game.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Creates the content for the game scene.
     *
     * @return The root parent node containing the game content.
     */
    public Parent createContent() {
        Pane root = new Pane();
        Canvas canvas = new Canvas(model.getSettings().getWorldSizeX(), model.getSettings().getWorldSizeY());
        root.getChildren().add(canvas);
        root.setPrefSize(model.getSettings().getWorldSizeX(), model.getSettings().getWorldSizeY());

        gc = canvas.getGraphicsContext2D();
        drawBackground(gc);


        foods.getChildren().addAll(generateFood());


        //drawScore();
        System.out.println(model.getSnake().getSnakeBody().get(0).getX());
        KeyFrame frame = new KeyFrame(Duration.seconds(model.getSettings().getDifficulty()), event -> {
            if (!model.isRunning())
                return;
            boolean toRemove = snakeDraw.getChildren().size() > 1;

            Node tail = toRemove ? snakeDraw.getChildren().remove(snakeDraw.getChildren().size()-1) : snakeDraw.getChildren().get(0);

            SnakePart tailMemory = new SnakePart((int)tail.getTranslateX(), (int)tail.getTranslateY());
            switch (model.getSnake().getDirection()) {
                case UP:
                    tail.setTranslateX(snakeDraw.getChildren().get(0).getTranslateX());
                    tail.setTranslateY(snakeDraw.getChildren().get(0).getTranslateY() - model.getSettings().getDisplaySize());
                    break;
                case DOWN:
                    tail.setTranslateX(snakeDraw.getChildren().get(0).getTranslateX());
                    tail.setTranslateY(snakeDraw.getChildren().get(0).getTranslateY() + model.getSettings().getDisplaySize());
                    break;
                case LEFT:
                    tail.setTranslateX(snakeDraw.getChildren().get(0).getTranslateX() - model.getSettings().getDisplaySize());
                    tail.setTranslateY(snakeDraw.getChildren().get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snakeDraw.getChildren().get(0).getTranslateX() + model.getSettings().getDisplaySize());
                    tail.setTranslateY(snakeDraw.getChildren().get(0).getTranslateY());
                    break;
            }

            System.out.println(model.getSnake().getSnakeBody().get(0).getX());

            if (toRemove) {
                snakeDraw.getChildren().add(0, tail);
            }

            model.setMoved(true);



            detectCollision(tail);

            if (tail.getTranslateX() < 0) {
                tail.setTranslateX(model.getSettings().getWorldSizeX() - model.getSettings().getDisplaySize());
            } else if (tail.getTranslateX() >= model.getSettings().getWorldSizeX() ) {
                tail.setTranslateX(0);
            }

            if (tail.getTranslateY() < model.getSettings().getDisplaySize()) {
                tail.setTranslateY(model.getSettings().getWorldSizeY() - model.getSettings().getDisplaySize());
            } else if (tail.getTranslateY() >= model.getSettings().getWorldSizeY() ) {
                tail.setTranslateY(model.getSettings().getDisplaySize());
            }

            for (Object food : foods.getChildren().toArray()) {
                eatFood(tail, (Rectangle)food, tailMemory);
            }

            drawScore();
        });

        snakeDraw.getChildren().addAll(drawSnake(model.getSnake().getSnakeBody()));
        root.getChildren().addAll(foods, snakeDraw);

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);


        return root;


    }
    /**
     * Detects collision between the snake and itself or the game boundaries.
     * If collision is detected, the game is restarted.
     *
     * @param tail The tail node of the snake.
     */
    public void detectCollision(Node tail) {
        for (Node rect : snakeDraw.getChildren()) {
            if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                    && tail.getTranslateY() == rect.getTranslateY()) {
                restartGame();
                startGameDraw();
                break;
            }
        }
    }

    /**
     * Restarts the game by stopping it, resetting the score, and starting a new game instance.
     */
    public void restartGame() {
        stopGame();
        model.startGame();
        timeline.play();
    }

    /**
     * Stops the game by setting the running flag to false, resetting the score, stopping the timeline, and clearing the snakeDraw group.
     */
    public void stopGame() {
        model.setRunning(false);
        model.setScore(0);
        timeline.stop();
        snakeDraw.getChildren().clear();
    }

    /**
     * Handles the action of eating food by the snake.
     * If the snake eats food, a new food item is generated, the score is updated, and the snake grows.
     *
     * @param tail        The tail node of the snake.
     * @param food        The food node being eaten.
     * @param tailMemory  The memory of the snake's tail before eating the food.
     */
    public void eatFood(Node tail, Rectangle food, SnakePart tailMemory) {
        if (tail.getTranslateX() == food.getTranslateX()
                && tail.getTranslateY() == food.getTranslateY()) {
            food.setTranslateX((int) (Math.random() * (model.getSettings().getWorldSizeX() - model.getSettings().getDisplaySize())) / model.getSettings().getDisplaySize() * model.getSettings().getDisplaySize());
            food.setTranslateY((int) (Math.random() * (model.getSettings().getWorldSizeY() - model.getSettings().getDisplaySize() - model.getSettings().getDisplaySize())) / model.getSettings().getDisplaySize() * model.getSettings().getDisplaySize() + model.getSettings().getDisplaySize());

            Rectangle rect = new Rectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize());
            rect.setTranslateX(tailMemory.getX());
            rect.setTranslateY(tailMemory.getY());
            rect.setFill(Color.BLUE);

            model.setScore(model.getScore() + 10);
            snakeDraw.getChildren().add(rect);
        }
    }

    /**
     * Initializes the game window and starts the game loop.
     * Handles user input for controlling the snake's direction.
     * Displays the game scene and starts the game rendering.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        model.setSnake(new Snake(new SnakePart(settings.getWorldSizeX() / 2, settings.getWorldSizeY() / 2), Direction.RIGHT));
        settings = model.getSettings();
        model.startGame();
        primaryStage.setTitle("Snake by Mello");
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(event -> {
            if (!model.isMoved())
                return;

            switch (event.getCode()) {
                case W:
                    if (model.getSnake().getDirection() != Direction.DOWN)
                        model.getSnake().setDirection(Direction.UP);
                    break;
                case S:
                    if (model.getSnake().getDirection() != Direction.UP)
                        model.getSnake().setDirection(Direction.DOWN);
                    break;
                case A:
                    if (model.getSnake().getDirection() != Direction.RIGHT)
                        model.getSnake().setDirection(Direction.LEFT);
                    break;
                case D:
                    if (model.getSnake().getDirection() != Direction.LEFT)
                        model.getSnake().setDirection(Direction.RIGHT);
                    break;
            }
            model.setMoved(false);
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        model.startGame();
        startGameDraw();
        startTimeline();
    }

    /**
     * Draws the snake on the game canvas based on its current state.
     *
     * @param snake The list of snake parts to be drawn.
     * @return ArrayList of Rectangles representing the drawn snake parts.
     */
    public ArrayList<Rectangle> drawSnake(ArrayList<SnakePart> snake) {
        // Clear previously drawn snake parts
        snakeDraw.getChildren().clear();

        // Create a new list to hold the drawn snake parts
        ArrayList<Rectangle> snakeDrawL = new ArrayList<>();
        for (SnakePart snakePart : snake) {
            Rectangle snakePartDraw = new Rectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize());
            snakePartDraw.setTranslateX(snakePart.getX());
            snakePartDraw.setTranslateY(snakePart.getY());
            snakePartDraw.setFill(Color.BLUE);
            snakeDrawL.add(snakePartDraw);
        }

        // Add newly drawn snake parts to the group
        return snakeDrawL;
    }

    /**
     * Initializes the game by drawing the snake's head on the canvas.
     */
    public void startGameDraw(){
        Rectangle head = new Rectangle(settings.getDisplaySize(), settings.getDisplaySize());
        head.setTranslateX(settings.getWorldSizeX() / 2);
        head.setTranslateY(settings.getWorldSizeY() / 2);
        head.setFill(Color.BLUE);
        snakeDraw.getChildren().add(head);
    }


    /**
     * Draws the background of the game canvas with alternating colors.
     *
     * @param gc The GraphicsContext of the canvas.
     */
    public void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < model.getSettings().getWorldSizeX() / model.getSettings().getDisplaySize(); i++) {
            for (int j = 0; j < model.getSettings().getWorldSizeY() / model.getSettings().getDisplaySize(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("44d420"));
                } else {
                    gc.setFill(Color.web("98e805"));
                }
                gc.fillRect(i * model.getSettings().getDisplaySize(), j * model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize());
            }
        }
    }

    /**
     * Generates food items at random positions on the game canvas.
     *
     * @return ArrayList of Rectangles representing the generated food items.
     */
    public ArrayList<Rectangle> generateFood() {
        ArrayList<Rectangle> foods = new ArrayList<>();

        for (int i = 0; i < model.getSettings().getFoodCount(); i++) {
            Rectangle food = new Rectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize());
            food.setFill(Color.ORANGERED);
            int x = model.getSettings().getWorldSizeX();
            int y = model.getSettings().getWorldSizeY();
            int displaySize = model.getSettings().getDisplaySize();
            food.setTranslateX((int) (Math.random() * (x - displaySize)) / displaySize * displaySize);
            food.setTranslateY((int) (Math.random() * (y - displaySize - displaySize)) / displaySize * displaySize + displaySize);

            foods.add(food);
        }
        return foods;
    }

    /**
     * Draws the score on the game canvas.
     */
    public void drawScore() {
        gc.clearRect(0, 0, model.getSettings().getWorldSizeX(), model.getSettings().getDisplaySize());
        for (int i = 0; i < model.getSettings().getWorldSizeX() / model.getSettings().getDisplaySize(); i++) {
            if ((i) % 2 == 0) {
                gc.setFill(Color.web("44d420"));
            } else {
                gc.setFill(Color.web("98e805"));
            }
            gc.fillRect(i * model.getSettings().getDisplaySize(), 0, model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize());
        }
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Consolas", model.getSettings().getDisplaySize() * 0.8));
        gc.fillText("Score: " + model.getScore(), 10, model.getSettings().getDisplaySize() * 0.8);
    }

    /**
     * Draws a rectangle on the game canvas.
     *
     * @param sizeX The width of the rectangle.
     * @param sizeY The height of the rectangle.
     * @param x The x-coordinate of the top-left corner of the rectangle.
     * @param y The y-coordinate of the top-left corner of the rectangle.
     * @param color The color of the rectangle.
     */
    public void drawRectangle(int sizeX, int sizeY, double x, double y, Color color) {
        Rectangle rect = new Rectangle(sizeX, sizeY);
        gc.setFill(color);
        gc.fillRect(x, y, model.getSettings().getWorldSizeX(), model.getSettings().getWorldSizeX());
        rect.setFill(color);
    }



    /**
     * Retrieves the Group containing the drawn snake.
     *
     * @return The Group containing the drawn snake.
     */
    public Group getSnakeDraw() {
        return snakeDraw;
    }

    /**
     * Starts the timeline for the game animation.
     */
    public void startTimeline(){
        timeline.play();
    }




}


