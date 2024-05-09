package ru.nsu.kozoliy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
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

import java.util.ArrayList;
public class SnakeGame extends Application {

    private GraphicsContext gc;
    
    Settings settings = new Settings(0.1, 800, 600,20,5);

    private Direction direction = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;
    private Timeline timeline = new Timeline();

    private int score = 0;

    private ObservableList<Node> snake;

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Parent createContent() {

        Pane root = new Pane();
        Canvas canvas = new Canvas(settings.getWorldSizeX(), settings.getWorldSizeY());
        root.getChildren().add(canvas);
        root.setPrefSize(settings.getWorldSizeX(), settings.getWorldSizeY());

        gc = canvas.getGraphicsContext2D();
        drawBackground(gc);



        Group snakeBody = new Group();
        snake = snakeBody.getChildren();
        Group foods = new Group();
        foods.getChildren().addAll(generateFood());

        KeyFrame frame = new KeyFrame(Duration.seconds(settings.getDifficulty()), event -> {
            if (!running)
                return;



            boolean toRemove = snake.size() > 1;

            Node tail = toRemove ? snake.remove(snake.size()-1) : snake.get(0);

            Point2D tailMemory = new Point2D(tail.getTranslateX(), tail.getTranslateY());

            changeDirection(tail);

            moved = true;

            if (toRemove) {
                snake.add(0, tail);
            }

            detectCollision(tail);

            if (tail.getTranslateX() < 0) {
                tail.setTranslateX(settings.getWorldSizeX() - settings.getDisplaySize());
            } else if (tail.getTranslateX() >= settings.getWorldSizeX() ) {
                tail.setTranslateX(0);
            }

            if (tail.getTranslateY() < settings.getDisplaySize()) {
                tail.setTranslateY(settings.getWorldSizeY() - settings.getDisplaySize());
            } else if (tail.getTranslateY() >= settings.getWorldSizeY() ) {
                tail.setTranslateY(settings.getDisplaySize());
            }

            for (Object food : foods.getChildren().toArray()) {
                eatFood(tail, (Rectangle)food, tailMemory);
            }

            drawScore();

        });


        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(foods, snakeBody);

        return root;
    }

    public ArrayList<Rectangle> generateFood() {
        ArrayList<Rectangle> foods = new ArrayList<>();

        for (int i = 0; i < settings.getFoodCount(); i++){
            Rectangle food = new Rectangle(settings.getDisplaySize(), settings.getDisplaySize());
            food.setFill(Color.ORANGERED);
            food.setTranslateX((int)(Math.random() * (settings.getWorldSizeX() - settings.getDisplaySize())) / settings.getDisplaySize() * settings.getDisplaySize());
            food.setTranslateY((int)(Math.random() * (settings.getWorldSizeY() - settings.getDisplaySize() - settings.getDisplaySize())) / settings.getDisplaySize() * settings.getDisplaySize() + settings.getDisplaySize());

            foods.add(food);
        }
        return foods;
    }

    public void restartGame() {
        stopGame();
        startGame();
    }

    public void changeDirection(Node tail) {
        switch (direction) {
            case UP:
                tail.setTranslateX(snake.get(0).getTranslateX());
                tail.setTranslateY(snake.get(0).getTranslateY() - settings.getDisplaySize());
                break;
            case DOWN:
                tail.setTranslateX(snake.get(0).getTranslateX());
                tail.setTranslateY(snake.get(0).getTranslateY() + settings.getDisplaySize());
                break;
            case LEFT:
                tail.setTranslateX(snake.get(0).getTranslateX() - settings.getDisplaySize());
                tail.setTranslateY(snake.get(0).getTranslateY());
                break;
            case RIGHT:
                tail.setTranslateX(snake.get(0).getTranslateX() + settings.getDisplaySize());
                tail.setTranslateY(snake.get(0).getTranslateY());
                break;
        }
    }

    public void detectCollision(Node tail) {
        for (Node rect : snake) {
            if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                    && tail.getTranslateY() == rect.getTranslateY()) {
                restartGame();
                break;
            }
        }
    }

    public void eatFood(Node tail, Rectangle food, Point2D tailMemory) {
        if (tail.getTranslateX() == food.getTranslateX()
                && tail.getTranslateY() == food.getTranslateY()) {
            food.setTranslateX((int)(Math.random() * (settings.getWorldSizeX() - settings.getDisplaySize())) / settings.getDisplaySize() * settings.getDisplaySize());
            food.setTranslateY((int)(Math.random() * (settings.getWorldSizeY() - settings.getDisplaySize() - settings.getDisplaySize())) / settings.getDisplaySize() * settings.getDisplaySize() + settings.getDisplaySize());

            Rectangle rect = new Rectangle(settings.getDisplaySize(), settings.getDisplaySize());
            rect.setTranslateX(tailMemory.getX());
            rect.setTranslateY(tailMemory.getY());
            rect.setFill(Color.BLUE);

            score += 10;
            snake.add(rect);
        }
    }


    public void stopGame() {
        running = false;
        score = 0;
        timeline.stop();
        snake.clear();
    }

    public void drawBackground(GraphicsContext gc) {
        for(int i = 0; i < settings.getWorldSizeX() / settings.getDisplaySize(); i++){
            for(int j = 1; j < settings.getWorldSizeY() / settings.getDisplaySize(); j++){
                if((i + j) % 2 == 0) {
                    gc.setFill(Color.web("44d420"));
                } else {
                    gc.setFill(Color.web("98e805"));
                }
                gc.fillRect(i * settings.getDisplaySize(), j * settings.getDisplaySize(), settings.getDisplaySize(), settings.getDisplaySize());
            }
        }
    }


    public void startGame() {
        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(settings.getDisplaySize(), settings.getDisplaySize());
        head.setTranslateX(settings.getWorldSizeX() / 2);
        head.setTranslateY(settings.getWorldSizeY() / 2);
        head.setFill(Color.BLUE);
        snake.add(head);
        System.out.println("ASdasd");
        timeline.play();
        running = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Snake by Mello");
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed(event -> {
            if (!moved)
                return;

            switch (event.getCode()) {
                case W:
                    if (direction != Direction.DOWN)
                        direction = Direction.UP;
                    break;
                case S:
                    if (direction != Direction.UP)
                        direction = Direction.DOWN;
                    break;
                case A:
                    if (direction != Direction.RIGHT)
                        direction = Direction.LEFT;
                    break;
                case D:
                    if (direction != Direction.LEFT)
                        direction = Direction.RIGHT;
                    break;
            }

            moved = false;
        });


        primaryStage.setScene(scene);
        primaryStage.show();

        startGame();

    }

    public void drawScore() {
        gc.clearRect(0, 0, settings.getWorldSizeX(), settings.getDisplaySize());
        for (int i = 0; i < settings.getWorldSizeX() / settings.getDisplaySize(); i++) {
            if((i) % 2 == 0) {
                gc.setFill(Color.web("44d420"));
            } else {
                gc.setFill(Color.web("98e805"));
            }
            gc.fillRect(i * settings.getDisplaySize(), 0, settings.getDisplaySize(), settings.getDisplaySize());
        }
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Consolas", settings.getDisplaySize() * 0.8));
        gc.fillText("Score: " + score, 10, settings.getDisplaySize() * 0.8);
    }


    public static void main(String[] args) {
        launch();
    }
}