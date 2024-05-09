package ru.nsu.kozoliy.view;

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

    private Group snakeDraw = new Group();

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

        System.out.println("Calling drawScore() method");

        //drawScore();
        System.out.println(model.getSnake().getSnakeBody().get(0).getX());
        KeyFrame frame = new KeyFrame(Duration.seconds(model.getSettings().getDifficulty()), event -> {
            //drawScore();

            if (!model.isRunning())
                return;


            boolean toRemove = snakeDraw.getChildren().size() > 1;

            Node tail = toRemove ? snakeDraw.getChildren().remove(snakeDraw.getChildren().size()-1) : snakeDraw.getChildren().get(0);



            SnakePart tailMemory = new SnakePart((int)tail.getTranslateX(), (int)tail.getTranslateY());
            //System.out.println(tailMemory.getX() + tailMemory.getY());
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
            //drawRectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize(), tail.getX(), tail.getY(), Color.BLUE);

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

    public void detectCollision(Node tail) {
        for (Node rect : snakeDraw.getChildren()) {
            if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                    && tail.getTranslateY() == rect.getTranslateY()) {
                restartGame();
                break;
            }
        }
    }

    public void restartGame() {
        stopGame();
        model.startGame();
        timeline.play();
    }

    public void stopGame() {
        model.setRunning(false);
        model.setScore(0);
        timeline.stop();
        snakeDraw.getChildren().clear();
    }
    public void eatFood(Node tail, Rectangle food, SnakePart tailMemory) {
        if (tail.getTranslateX() == food.getTranslateX()
                && tail.getTranslateY() == food.getTranslateY()) {
            food.setTranslateX((int)(Math.random() * (model.getSettings().getWorldSizeX() - model.getSettings().getDisplaySize())) / model.getSettings().getDisplaySize() * model.getSettings().getDisplaySize());
            food.setTranslateY((int)(Math.random() * (model.getSettings().getWorldSizeY() - model.getSettings().getDisplaySize() - model.getSettings().getDisplaySize())) / model.getSettings().getDisplaySize() * model.getSettings().getDisplaySize() + model.getSettings().getDisplaySize());

            Rectangle rect = new Rectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize());
            rect.setTranslateX(tailMemory.getX());
            rect.setTranslateY(tailMemory.getY());
            rect.setFill(Color.BLUE);

            model.setScore(model.getScore() + 10);
            snakeDraw.getChildren().add(rect);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Calling start() method");
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
        startTimeline();
    }



    public ArrayList<Rectangle> drawSnake(ArrayList<SnakePart> snake) {
        // Очищаем предыдущие отрисованные части змеи
        snakeDraw.getChildren().clear();

        // Создаем новый список для отрисованных частей змеи
        ArrayList<Rectangle> snakeDrawL = new ArrayList<>();
        for (SnakePart snakePart : snake) {
            Rectangle snakePartDraw = new Rectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize());
            snakePartDraw.setTranslateX(snakePart.getX());
            snakePartDraw.setTranslateY(snakePart.getY());
            snakePartDraw.setFill(Color.BLUE);
            snakeDrawL.add(snakePartDraw);
        }

        // Добавляем новые отрисованные части змеи в группу
        return snakeDrawL;
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
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Consolas", model.getSettings().getDisplaySize() * 0.8));
        gc.fillText("Score: " + model.getScore(), 10, model.getSettings().getDisplaySize() * 0.8);
    }

    public void drawRectangle(int sizeX, int sizeY, double x, double y, Color color) {
        Rectangle rect = new Rectangle(sizeX, sizeY);
        gc.setFill(color);
        gc.fillRect(x, y, model.getSettings().getWorldSizeX(), model.getSettings().getWorldSizeX());


        rect.setFill(color);
    }

    public static void main(String[] args) {
        launch();
    }

    public Group getSnakeDraw() {
        return snakeDraw;
    }

    public void stopTimeline(){
        timeline.stop();
    }

    public void startTimeline(){
        System.out.println("timeline");
        timeline.play();
    }

    public void restartTimeline(){
        stopTimeline();
        startTimeline();
    }


}


