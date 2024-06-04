package ru.nsu.kozoliy.view;

import java.util.ArrayList;
import java.util.Random;
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
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.models.BadSnake;
import ru.nsu.kozoliy.models.Direction;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.Settings;
import ru.nsu.kozoliy.models.SmartSnake;
import ru.nsu.kozoliy.models.Snake;
import ru.nsu.kozoliy.models.SnakePart;


/**
 * Class for snake game presentation.
 *
 */
public class SnakeGameView extends Application {

    private final Model model;
    private GraphicsContext gc;
    public Timeline timeline = new Timeline();
    private final Group foods = new Group();
    private final Group snakeDraw = new Group();
    private final Random random = new Random();
    private Settings settings;
    private BadSnake badSnake;
    private final Group badSnakeDraw = new Group();
    private final Timeline badSnakeTimeline = new Timeline();
    private SmartSnake smartSnake;
    private final Group smartSnakeDraw = new Group();
    private final Timeline smartSnakeTimeLine = new Timeline();


    /**
     * Food getter.
     *
     */
    @ExcludeFromJacocoGeneratedReport
    public Group getFoods() {
        return foods;
    }

    /**
     * Constructs a SnakeGameView instance.
     *
     * @param model    The model of the Snake game.
     * @param settings The settings of the Snake game.
     */
    public SnakeGameView(Model model, Settings settings) {
        this.model = model;
        this.settings = settings;
    }


    /**
     * Creates the content for the game scene.
     *
     * @return The root parent node containing the game content.
     */
    @ExcludeFromJacocoGeneratedReport
    public Parent createContent() {
        Pane root = new Pane();
        Canvas canvas = new Canvas(model.getSettings().getWorldSizeX(),
                model.getSettings().getWorldSizeY());
        root.getChildren().add(canvas);
        root.setPrefSize(model.getSettings().getWorldSizeX(),
                model.getSettings().getWorldSizeY());

        gc = canvas.getGraphicsContext2D();
        drawBackground(gc);


        foods.getChildren().addAll(generateFood());

        System.out.println(model.getSnake().getSnakeBody().get(0).getX());
        KeyFrame frame = new KeyFrame(Duration.seconds(model.getSettings()
                .getDifficulty()), event -> {
            if (!model.isRunning()) {
                return;
            }

            boolean toRemove = snakeDraw.getChildren().size() > 1;

            Node tail = toRemove ? snakeDraw.getChildren()
                    .remove(snakeDraw.getChildren().size() - 1)
                    : snakeDraw.getChildren().get(0);

            SnakePart tailMemory = new SnakePart((int) tail.getTranslateX(),
                    (int) tail.getTranslateY());

            switch (model.getSnake().getDirection()) {
                case UP:
                    tail.setTranslateX(snakeDraw.getChildren().get(0).getTranslateX());
                    tail.setTranslateY(snakeDraw.getChildren().get(0).getTranslateY()
                            - model.getSettings().getDisplaySize());
                    break;
                case DOWN:
                    tail.setTranslateX(snakeDraw.getChildren().get(0).getTranslateX());
                    tail.setTranslateY(snakeDraw.getChildren().get(0).getTranslateY()
                            + model.getSettings().getDisplaySize());
                    break;
                case LEFT:
                    tail.setTranslateX(snakeDraw.getChildren().get(0).getTranslateX()
                            - model.getSettings().getDisplaySize());
                    tail.setTranslateY(snakeDraw.getChildren().get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snakeDraw.getChildren().get(0).getTranslateX()
                            + model.getSettings().getDisplaySize());
                    tail.setTranslateY(snakeDraw.getChildren().get(0).getTranslateY());
                    break;
                default:
                    break;
            }

            System.out.println(model.getSnake().getSnakeBody().get(0).getX());

            if (toRemove) {
                snakeDraw.getChildren().add(0, tail);
            }

            model.setMoved(true);

            detectCollision(tail);
            detectBadSnakeCollision();
            detectSmartSnakeCollision();


            Node badSnakeHead = badSnakeDraw.getChildren().get(0);
            Node smartSnakeHead = smartSnakeDraw.getChildren().get(0);

            for (int i = 0; i < snakeDraw.getChildren().size(); i++) {
                Node snakePart = snakeDraw.getChildren().get(i);

                if (snakePart.getTranslateX() == badSnakeHead.getTranslateX()
                        && snakePart.getTranslateY() == badSnakeHead.getTranslateY()) {
                    if (badSnake.getSnakeBody().size() == 1) {
                        badSnake.getSnakeBody().get(0).setX(
                                model.getSettings().getWorldSizeX() / 2);
                        badSnake.getSnakeBody().get(0).setY(
                                model.getSettings().getWorldSizeY() / 2);
                    } else {
                        badSnake.getSnakeBody().remove(badSnake.getSnakeBody().size() - 1);
                    }
                }
                if (snakePart.getTranslateX() == smartSnakeHead.getTranslateX()
                        && snakePart.getTranslateY() == smartSnakeHead.getTranslateY()) {

                    for ( int j = 0; j < smartSnake.getSnakeBody().size() - 1; j++) {
                        smartSnake.getSnakeBody().remove(j);
                    }
                    smartSnake.getSnakeBody().get(0).setX(model.getSettings().getWorldSizeX() / 2);
                    smartSnake.getSnakeBody().get(0).setY(model.getSettings().getWorldSizeY() / 2);
                }
            }

            if (tail.getTranslateX() < 0) {
                tail.setTranslateX(model.getSettings().getWorldSizeX()
                        - model.getSettings().getDisplaySize());
            } else if (tail.getTranslateX() >= model.getSettings().getWorldSizeX()) {
                tail.setTranslateX(0);
            }

            if (tail.getTranslateY() < model.getSettings().getDisplaySize()) {
                tail.setTranslateY(model.getSettings().getWorldSizeY()
                        - model.getSettings().getDisplaySize());
            } else if (tail.getTranslateY() >= model.getSettings().getWorldSizeY()) {
                tail.setTranslateY(model.getSettings().getDisplaySize());
            }

            for (Object food : foods.getChildren().toArray()) {
                eatFood(tail, (Rectangle) food, tailMemory);
            }

            for (int i = 0; i < foods.getChildren().size(); i++) {
                if (badSnake.getSnakeBody().get(0).getX()
                        == foods.getChildren().get(i).getTranslateX()
                        && badSnake.getSnakeBody().get(0).getY()
                        == foods.getChildren().get(i).getTranslateY()) {
                    badSnake.getSnakeBody().add(new SnakePart(20 , 40));
                }
                if (smartSnake.getSnakeBody().get(0).getX()
                        == foods.getChildren().get(i).getTranslateX()
                        && smartSnake.getSnakeBody().get(0).getY()
                                == foods.getChildren().get(i).getTranslateY()) {
                    smartSnake.getSnakeBody().add(new SnakePart(20, 40));
                }
            }

            drawScore();
        });

        snakeDraw.getChildren().addAll(drawSnake(model.getSnake().getSnakeBody()));
        root.getChildren().addAll(foods, snakeDraw, badSnakeDraw, smartSnakeDraw);

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        return root;
    }


    /**
     * Detects collision between the snake and other game elements.
     *
     * @param tail The tail node of the snake.
     */
    @ExcludeFromJacocoGeneratedReport
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

    @ExcludeFromJacocoGeneratedReport
    public void restartGame() {
        stopGame();
        model.startGame();
        timeline.play();
    }

    @ExcludeFromJacocoGeneratedReport
    public void stopGame() {
        model.setRunning(false);
        model.setScore(0);
        timeline.stop();
        snakeDraw.getChildren().clear();
    }


    /**
     * Replace food. Add element to snake body.
     */
    @ExcludeFromJacocoGeneratedReport
    public void eatFood(Node tail, Rectangle food, SnakePart tailMemory) {
        if (tail.getTranslateX() == food.getTranslateX()
                && tail.getTranslateY() == food.getTranslateY()) {
            food.setTranslateX((int) (Math.random() * (model.getSettings().getWorldSizeX()
                    - model.getSettings().getDisplaySize()))
                    / model.getSettings().getDisplaySize()
                    * model.getSettings().getDisplaySize());
            food.setTranslateY((int) (Math.random() * (model.getSettings().getWorldSizeY()
                    - model.getSettings().getDisplaySize()
                    - model.getSettings().getDisplaySize()))
                    / model.getSettings().getDisplaySize()
                    * model.getSettings().getDisplaySize()
                    + model.getSettings().getDisplaySize());

            Rectangle rect = new Rectangle(model.getSettings().getDisplaySize(),
                    model.getSettings().getDisplaySize());
            rect.setTranslateX(tailMemory.getX());
            rect.setTranslateY(tailMemory.getY());
            rect.setFill(Color.BLUE);

            model.setScore(model.getScore() + 10);
            snakeDraw.getChildren().add(rect);
        }
    }



    @Override
    @ExcludeFromJacocoGeneratedReport
    public void start(Stage primaryStage) {
        model.setSnake(new Snake(new SnakePart(settings.getWorldSizeX() / 2,
                settings.getWorldSizeY() / 2),
                Direction.RIGHT));
        settings = model.getSettings();
        model.startGame();
        primaryStage.setTitle("Snake by Mello");
        Scene scene = new Scene(createContent());

        badSnake = new BadSnake(model.getSettings().getDisplaySize(),
                model.getSettings().getDisplaySize(),
                settings.getDisplaySize(),
                settings.getWorldSizeX(),
                settings.getWorldSizeY());
        KeyFrame badSnakeFrame = new KeyFrame(Duration.seconds(
                model.getSettings().getDifficulty()), event -> {
            badSnake.move();

            if (random.nextInt(100) < 10) { // 10% chance to change direction each frame
                badSnake.changeDirection();
            }

            drawBadSnake();
        });

        smartSnake = new SmartSnake(settings.getWorldSizeX() - settings.getDisplaySize(),
                settings.getWorldSizeY() - settings.getDisplaySize(),
                settings.getDisplaySize(),
                settings.getWorldSizeX(),
                settings.getWorldSizeY());
        KeyFrame smartSnakeFrame = new KeyFrame(Duration.seconds(
                model.getSettings().getDifficulty()), event -> {
            moveSmartSnake();

            smartSnake.move();

            drawSmartSnake();
        });

        scene.setOnKeyPressed(event -> {
            if (!model.isMoved())
                return;

            switch (event.getCode()) {
                case W:
                    if (model.getSnake().getDirection() != Direction.DOWN) {
                        model.getSnake().setDirection(Direction.UP);
                    }
                    break;
                case S:
                    if (model.getSnake().getDirection() != Direction.UP){
                        model.getSnake().setDirection(Direction.DOWN);
                    }
                    break;
                case A:
                    if (model.getSnake().getDirection() != Direction.RIGHT){
                        model.getSnake().setDirection(Direction.LEFT);
                    }
                    break;
                case D:
                    if (model.getSnake().getDirection() != Direction.LEFT) {
                        model.getSnake().setDirection(Direction.RIGHT);
                    }
                    break;
                default:
                    break;
            }
            model.setMoved(false);
        });

        badSnakeTimeline.getKeyFrames().add(badSnakeFrame);
        badSnakeTimeline.setCycleCount(Timeline.INDEFINITE);
        badSnakeTimeline.play();

        smartSnakeTimeLine.getKeyFrames().add(smartSnakeFrame);
        smartSnakeTimeLine.setCycleCount(Timeline.INDEFINITE);
        smartSnakeTimeLine.play();

        primaryStage.setScene(scene);
        primaryStage.show();

        model.startGame();
        startGameDraw();
        startTimeline();
    }

    @ExcludeFromJacocoGeneratedReport
    public void drawBadSnake() {
        badSnakeDraw.getChildren().clear();
        for (SnakePart part : badSnake.getSnakeBody()) {
            Rectangle rect = new Rectangle(settings.getDisplaySize(), settings.getDisplaySize());
            rect.setTranslateX(part.getX());
            rect.setTranslateY(part.getY());
            rect.setFill(Color.RED);
            badSnakeDraw.getChildren().add(rect);
        }
    }

    @ExcludeFromJacocoGeneratedReport
    public void drawSmartSnake() {
        smartSnakeDraw.getChildren().clear();
        for (SnakePart part : smartSnake.getSnakeBody()) {
            Rectangle rect = new Rectangle(settings.getDisplaySize(), settings.getDisplaySize());
            rect.setTranslateX(part.getX());
            rect.setTranslateY(part.getY());
            rect.setFill(Color.PURPLE);
            smartSnakeDraw.getChildren().add(rect);
        }
    }

    /**
     * Moves the smart snake towards the nearest food item.
     */
    @ExcludeFromJacocoGeneratedReport
    public void moveSmartSnake() {
        if (!smartSnake.getSnakeBody().isEmpty()) {
            SnakePart head = smartSnake.getSnakeBody().get(0);


            Rectangle nearestFood = null;
            double minDistance = Double.MAX_VALUE;
            for (Node foodNode : foods.getChildren()) {
                Rectangle food = (Rectangle) foodNode;
                double distance = Math.sqrt(Math.pow(food.getTranslateX() - head.getX(), 2)
                        + Math.pow(food.getTranslateY() - head.getY(), 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestFood = food;
                }
            }

            if (nearestFood != null) {
                double foodX = nearestFood.getTranslateX();
                double foodY = nearestFood.getTranslateY();

                if (head.getX() < foodX) {
                    smartSnake.setDirection(Direction.RIGHT);
                } else if (head.getX() > foodX) {
                    smartSnake.setDirection(Direction.LEFT);
                } else if (head.getY() < foodY) {
                    smartSnake.setDirection(Direction.DOWN);
                } else if (head.getY() > foodY) {
                    smartSnake.setDirection(Direction.UP);
                }

                for (Object food : foods.getChildren().toArray()) {
                    if (head.getX() == ((Rectangle) food).getTranslateX()
                            && head.getY() == ((Rectangle) food).getTranslateY()) {
                        ((Rectangle)food).setTranslateX((int) (Math.random()
                                * (model.getSettings().getWorldSizeX()
                                - model.getSettings().getDisplaySize()))
                                / model.getSettings().getDisplaySize()
                                * model.getSettings().getDisplaySize());
                        ((Rectangle)food).setTranslateY((int) (Math.random()
                                * (model.getSettings().getWorldSizeY()
                                - model.getSettings().getDisplaySize()
                                - model.getSettings().getDisplaySize()))
                                / model.getSettings().getDisplaySize()
                                * model.getSettings().getDisplaySize()
                                + model.getSettings().getDisplaySize());
                    }
                }
            }
        }
    }


    @ExcludeFromJacocoGeneratedReport
    public void detectBadSnakeCollision() {
        Node playerHead = snakeDraw.getChildren().get(0);

        for (int i = 0; i < badSnakeDraw.getChildren().size(); i++) {
            Node badSnakeHead = badSnakeDraw.getChildren().get(i);
            if (playerHead.getTranslateX() == badSnakeHead.getTranslateX()
                    && playerHead.getTranslateY() == badSnakeHead.getTranslateY()) {
                restartGame();
                startGameDraw();
            }
        }

        Node smartSnakeHead = smartSnakeDraw.getChildren().get(0);

        for (int i = 0; i < badSnakeDraw.getChildren().size(); i++) {
            Node badSnakeHead = badSnakeDraw.getChildren().get(i);
            if (smartSnakeHead.getTranslateX() == badSnakeHead.getTranslateX()
                    && smartSnakeHead.getTranslateY() == badSnakeHead.getTranslateY()) {
                smartSnake.getSnakeBody().get(0).setX(model.getSettings().getWorldSizeX() / 2);
                smartSnake.getSnakeBody().get(0).setY(model.getSettings().getWorldSizeY() / 2);
            }
        }
    }

    @ExcludeFromJacocoGeneratedReport
    public void detectSmartSnakeCollision() {
        Node playerHead = snakeDraw.getChildren().get(0);

        for (int i = 0; i < smartSnakeDraw.getChildren().size(); i++) {
            Node smartSnakeHead = smartSnakeDraw.getChildren().get(i);
            if (playerHead.getTranslateX() == smartSnakeHead.getTranslateX()
                    && playerHead.getTranslateY() == smartSnakeHead.getTranslateY()) {
                restartGame();
                startGameDraw();
            }
        }

        Node badSnakeHead = badSnakeDraw.getChildren().get(0);

        for (int i = 0; i < smartSnakeDraw.getChildren().size(); i++) {
            Node smartSnakeHead = smartSnakeDraw.getChildren().get(i);
            if (smartSnakeHead.getTranslateX() == badSnakeHead.getTranslateX()
                    && smartSnakeHead.getTranslateY() == badSnakeHead.getTranslateY()) {
                if (badSnake.getSnakeBody().size() == 1) {
                    badSnake.getSnakeBody().get(0).setX(model.getSettings().getWorldSizeX() / 2);
                    badSnake.getSnakeBody().get(0).setY(model.getSettings().getWorldSizeY() / 2);
                } else {
                    badSnake.getSnakeBody().remove(badSnake.getSnakeBody().size() - 1);
                }
            }
        }
    }



    /**
     * Draw snake rectangles on gameworld.
     *
     */
    public ArrayList<Rectangle> drawSnake(ArrayList<SnakePart> snake) {
        snakeDraw.getChildren().clear();

        ArrayList<Rectangle> snakeDrawL = new ArrayList<>();
        for (SnakePart snakePart : snake) {
            Rectangle snakePartDraw = new Rectangle(model.getSettings().getDisplaySize(),
                    model.getSettings().getDisplaySize());
            snakePartDraw.setTranslateX(snakePart.getX());
            snakePartDraw.setTranslateY(snakePart.getY());
            snakePartDraw.setFill(Color.BLUE);
            snakeDrawL.add(snakePartDraw);
        }

        return snakeDrawL;
    }

    @ExcludeFromJacocoGeneratedReport
    public void startGameDraw(){
        Rectangle head = new Rectangle(settings.getDisplaySize(), settings.getDisplaySize());
        head.setTranslateX(settings.getWorldSizeX() / 2);
        head.setTranslateY(settings.getWorldSizeY() / 2);
        head.setFill(Color.BLUE);
        snakeDraw.getChildren().add(head);
    }

    @ExcludeFromJacocoGeneratedReport
    public void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < model.getSettings().getWorldSizeX() / model.getSettings().getDisplaySize(); i++) {
            for (int j = 0; j < model.getSettings().getWorldSizeY() / model.getSettings().getDisplaySize(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("44d420"));
                } else {
                    gc.setFill(Color.web("98e805"));
                }
                gc.fillRect(i * model.getSettings().getDisplaySize(),
                        j * model.getSettings().getDisplaySize(),
                        model.getSettings().getDisplaySize(),
                        model.getSettings().getDisplaySize());
            }
        }
    }


    public ArrayList<Rectangle> generateFood() {
        ArrayList<Rectangle> foods = new ArrayList<>();

        for (int i = 0; i < model.getSettings().getFoodCount(); i++) {
            Rectangle food = new Rectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize());
            food.setFill(Color.ORANGERED);
            int x = model.getSettings().getWorldSizeX();
            int y = model.getSettings().getWorldSizeY();
            int displaySize = model.getSettings().getDisplaySize();
            food.setTranslateX((int) (Math.random() * (x - displaySize)) / displaySize * displaySize);
            food.setTranslateY((int) (Math.random() * (y - displaySize - displaySize))
                    / displaySize * displaySize + displaySize);

            foods.add(food);
        }
        return foods;
    }

    @ExcludeFromJacocoGeneratedReport
    public void drawScore() {
        gc.clearRect(0, 0, model.getSettings().getWorldSizeX(), model.getSettings().getDisplaySize());
        for (int i = 0; i < model.getSettings().getWorldSizeX() / model.getSettings().getDisplaySize(); i++) {
            if ((i) % 2 == 0) {
                gc.setFill(Color.web("44d420"));
            } else {
                gc.setFill(Color.web("98e805"));
            }
            gc.fillRect(i * model.getSettings().getDisplaySize(), 0, model.getSettings().getDisplaySize(),
                    model.getSettings().getDisplaySize());
        }
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Consolas", model.getSettings().getDisplaySize() * 0.8));
        gc.fillText("Score: " + model.getScore(), 10, model.getSettings().getDisplaySize() * 0.8);
    }

    @ExcludeFromJacocoGeneratedReport
    public void drawRectangle(int sizeX, int sizeY, double x, double y, Color color) {
        Rectangle rect = new Rectangle(sizeX, sizeY);
        gc.setFill(color);
        gc.fillRect(x, y, model.getSettings().getWorldSizeX(), model.getSettings().getWorldSizeX());
        rect.setFill(color);
    }

    @ExcludeFromJacocoGeneratedReport
    public Group getSnakeDraw() {
        return snakeDraw;
    }

    @ExcludeFromJacocoGeneratedReport
    public void startTimeline(){
        timeline.play();
    }
}


