package ru.nsu.kozoliy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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
import java.util.Objects;

public class Main extends Application {

    private GraphicsContext gc;
    private static final double SPEED = 0.1;
    public static final int BLOCK_SIZE = 20;
    public static final int FOOD_COUNT = 50;
    public static final int APP_W = 600;
    public static final int APP_H = 800;
    private Direction direction = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;
    private Timeline timeline = new Timeline();

    private int score = 0;

    private ObservableList<Node> snake;

    private Parent createContent() {

        Pane root = new Pane();
        Canvas canvas = new Canvas(APP_W, APP_H);
        root.getChildren().add(canvas);
        root.setPrefSize(APP_W, APP_H);

        gc = canvas.getGraphicsContext2D();
        drawBackground(gc);


        Group snakeBody = new Group();
        snake = snakeBody.getChildren();
        Group foods = new Group();
        foods.getChildren().addAll(generateFood());

        KeyFrame frame = new KeyFrame(Duration.seconds(SPEED), event -> {
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
                tail.setTranslateX(APP_W - BLOCK_SIZE);
            } else if (tail.getTranslateX() >= APP_W ) {
                tail.setTranslateX(0);
            }

            if (tail.getTranslateY() < BLOCK_SIZE) {
                tail.setTranslateY(APP_H - BLOCK_SIZE);
            } else if (tail.getTranslateY() >= APP_H ) {
                tail.setTranslateY(BLOCK_SIZE);
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

    private ArrayList<Rectangle> generateFood() {
        ArrayList<Rectangle> foods = new ArrayList<>();

        for (int i = 0; i < Main.FOOD_COUNT; i++){
            Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
            food.setFill(Color.ORANGERED);
            food.setTranslateX((int)(Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
            food.setTranslateY((int)(Math.random() * (APP_H - BLOCK_SIZE - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE + BLOCK_SIZE);

            foods.add(food);
        }
        return foods;


    }
    private void restartGame() {
        stopGame();
        startGame();
    }

    private void changeDirection(Node tail) {
        switch (direction) {
            case UP:
                tail.setTranslateX(snake.get(0).getTranslateX());
                tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                break;
            case DOWN:
                tail.setTranslateX(snake.get(0).getTranslateX());
                tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                break;
            case LEFT:
                tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                tail.setTranslateY(snake.get(0).getTranslateY());
                break;
            case RIGHT:
                tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                tail.setTranslateY(snake.get(0).getTranslateY());
                break;
        }
    }

    private void detectCollision(Node tail) {
        for (Node rect : snake) {
            if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                    && tail.getTranslateY() == rect.getTranslateY()) {
                restartGame();
                break;
            }
        }
    }

    private void eatFood(Node tail, Rectangle food, Point2D tailMemory) {
        if (tail.getTranslateX() == food.getTranslateX()
                && tail.getTranslateY() == food.getTranslateY()) {
            food.setTranslateX((int)(Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
            food.setTranslateY((int)(Math.random() * (APP_H - BLOCK_SIZE - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE + BLOCK_SIZE);

            Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
            rect.setTranslateX(tailMemory.getX());
            rect.setTranslateY(tailMemory.getY());
            rect.setFill(Color.BLUE);

            score += 10;
            snake.add(rect);
        }
    }


    private void stopGame() {
        running = false;
        score = 0;
        timeline.stop();
        snake.clear();
    }

    private void drawBackground(GraphicsContext gc) {
        for(int i = 0; i < APP_W / BLOCK_SIZE; i++){
            for(int j = 0; j < APP_H / BLOCK_SIZE; j++){
                if((i + j) % 2 == 0) {
                    gc.setFill(Color.web("44d420"));
                } else {
                    gc.setFill(Color.web("98e805"));
                }
                gc.fillRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }


    private void startGame() {
        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        head.setTranslateX(APP_W / 2);
        head.setTranslateY(APP_H / 2);
        head.setFill(Color.BLUE);
        snake.add(head);
        timeline.play();
        running = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setResizable(false);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);

            primaryStage.show();
            primaryStage.setTitle("Snake by Mello");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Scene scene = new Scene(createContent());
        /*

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

         */

        //primaryStage.setScene(scene);
        //primaryStage.show();

        //startGame();

    }

    private void drawScore() {
        gc.clearRect(0, 0, APP_W, BLOCK_SIZE);
        for (int i = 0; i < APP_W / BLOCK_SIZE; i++) {
            if((i) % 2 == 0) {
                gc.setFill(Color.web("44d420"));
            } else {
                gc.setFill(Color.web("98e805"));
            }
            gc.fillRect(i * BLOCK_SIZE, 0, BLOCK_SIZE, BLOCK_SIZE);
        }
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Consolas", BLOCK_SIZE * 0.8));
        gc.fillText("Score: " + score, 10, BLOCK_SIZE * 0.8);
    }




    public static void main(String[] args) {
        launch(args);
    }
}