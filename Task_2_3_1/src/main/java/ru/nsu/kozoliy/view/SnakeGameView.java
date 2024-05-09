package ru.nsu.kozoliy.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
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
import ru.nsu.kozoliy.Direction;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.Snake;
import ru.nsu.kozoliy.models.SnakePart;
import ru.nsu.kozoliy.viewModel.SnakeGameViewModel;

import java.util.ArrayList;

public class SnakeGameView extends Application {

    private final Model model;
    private final SnakeGameViewModel snakeGameViewModel;
    private GraphicsContext gc;
    private Timeline timeline = new Timeline();
    private Group foods = new Group();

    public Group getFoods() {
        return foods;
    }

    public SnakeGameView(Model model, SnakeGameViewModel snakeGameViewModel) {
        this.model = model;
        this.snakeGameViewModel = snakeGameViewModel;
    }

    public Parent createContent() {

        Pane root = new Pane();
        Canvas canvas = new Canvas(model.getSettings().getWorldSizeX(), model.getSettings().getWorldSizeY());
        root.getChildren().add(canvas);
        root.setPrefSize(model.getSettings().getWorldSizeX(), model.getSettings().getWorldSizeY());

        gc = canvas.getGraphicsContext2D();
        drawBackground(gc);


        foods.getChildren().addAll(generateFood());

        KeyFrame frame = new KeyFrame(Duration.seconds(model.getSettings().getDifficulty()), event -> {
            if (!model.isRunning())
                return;


            boolean toRemove = model.getSnake().getSize() > 1;

            Node tail = null;
            if (toRemove) {
                tail.setTranslateX(model.getSnake().getSnakeBody().remove(model.getSnake().getSize() - 1).getX());
                tail.setTranslateY(model.getSnake().getSnakeBody().remove(model.getSnake().getSize() - 1).getY());
            } else {
                tail.setTranslateX(model.getSnake().getSnakeBody().get(0).getX());
                tail.setTranslateY(model.getSnake().getSnakeBody().get(0).getY());
            }

            SnakePart tailMemory = new SnakePart((int)tail.getTranslateX(), (int)tail.getTranslateY());
            changeDirection(tail);

            model.setMoved(true);

            if (toRemove) {
                model.getSnake().getSnakeBody().add(0, new SnakePart((int)tail.getTranslateX(), (int)tail.getTranslateY()));
            }

            snakeGameViewModel.move(tail, tailMemory);

            drawScore();
        });


        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);


        Group snakeBody = new Group();
        snakeBody.getChildren().addAll(drawSnake(model.getSnake().getSnakeBody()));
        root.getChildren().addAll(foods, snakeBody);


        return root;
    }

    public void changeDirection(Node tail) {
        switch (model.getSnake().getDirection()) {
            case UP:
                tail.setTranslateX(model.getSnake().getSnakeBody().get(0).getX());
                tail.setTranslateY(model.getSnake().getSnakeBody().get(0).getY() - model.getSettings().getDisplaySize());
                break;
            case DOWN:
                tail.setTranslateX(model.getSnake().getSnakeBody().get(0).getX());
                tail.setTranslateY(model.getSnake().getSnakeBody().get(0).getY() + model.getSettings().getDisplaySize());
                break;
            case LEFT:
                tail.setTranslateX(model.getSnake().getSnakeBody().get(0).getX() - model.getSettings().getDisplaySize());
                tail.setTranslateY(model.getSnake().getSnakeBody().get(0).getY());
                break;
            case RIGHT:
                tail.setTranslateX(model.getSnake().getSnakeBody().get(0).getX() + model.getSettings().getDisplaySize());
                tail.setTranslateY(model.getSnake().getSnakeBody().get(0).getY());
                break;
        }
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake by Mello");
        model.setSnake(new Snake(0,0, Direction.DOWN));
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
    }



    public ArrayList<Node> drawSnake(ArrayList<SnakePart> snake) {
        ArrayList<Node> snakeDraw = new ArrayList<>();
        for (SnakePart snakePart : snake) {
            Rectangle snakePartDraw = new Rectangle();
            snakePartDraw.setTranslateX(snakePart.getX());
            snakePartDraw.setTranslateY(snakePart.getY());
            snakePartDraw.setFill(Color.BLUE);
            snakeDraw.add(snakePartDraw);
        }
        return snakeDraw;
    }



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
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Consolas", model.getSettings().getDisplaySize() * 0.8));
        gc.fillText("Score: " + model.getScore(), 10, model.getSettings().getDisplaySize() * 0.8);
    }

    public void drawRectangle(int sizeX, int sizeY, double x, double y, Color color) {
        Rectangle rect = new Rectangle(sizeX, sizeY);
        rect.setTranslateX(x);
        rect.setTranslateY(y);
        rect.setFill(color);
    }

    public static void main(String[] args) {
        launch();
    }

    public void stopTimeline(){
        timeline.stop();
    }

    public void startTimeline(){
        timeline.play();
    }


}


