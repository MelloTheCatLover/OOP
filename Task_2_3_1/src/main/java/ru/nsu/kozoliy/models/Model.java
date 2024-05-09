package ru.nsu.kozoliy.models;


import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ru.nsu.kozoliy.Direction;
import ru.nsu.kozoliy.Settings;
import ru.nsu.kozoliy.view.SnakeGameView;
import ru.nsu.kozoliy.viewModel.SnakeGameViewModel;

public class Model {

    SnakeGameViewModel snakeGameViewModel = new SnakeGameViewModel(this);

    private boolean moved = false;

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isMoved() {
        return moved;
    }

    public boolean isRunning() {
        return running;
    }

    private boolean running = false;
    private Settings settings = new Settings(0.1, 800, 600,20,5);

    private ru.nsu.kozoliy.models.Snake snake;

    private int score = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Settings getSettings() {
        return settings;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public SnakePart changeDirection(SnakePart tail) {
        SnakePart newTail = new SnakePart(tail.getX(), tail.getY());
        switch (this.getSnake().getDirection()) {
            case UP:
                newTail.setX(this.getSnake().getSnakeBody().get(0).getX());
                newTail.setY(this.getSnake().getSnakeBody().get(0).getY() - this.getSettings().getDisplaySize());
                break;
            case DOWN:
                newTail.setX(this.getSnake().getSnakeBody().get(0).getX());
                newTail.setY(this.getSnake().getSnakeBody().get(0).getY() + this.getSettings().getDisplaySize());
                break;
            case LEFT:
                newTail.setX(this.getSnake().getSnakeBody().get(0).getX() - this.getSettings().getDisplaySize());
                newTail.setY(this.getSnake().getSnakeBody().get(0).getY());
                break;
            case RIGHT:
                newTail.setX(this.getSnake().getSnakeBody().get(0).getX() + settings.getDisplaySize());
                newTail.setY(this.getSnake().getSnakeBody().get(0).getY());
                break;
        }
        return newTail;
    }

    public void detectCollision(SnakePart tail) {
        for (SnakePart part : snake.getSnakeBody()) {
            if (part != tail && tail.getX() == part.getX()
                    && tail.getY() == part.getY()) {
                restartGame();
                break;
            }
        }
    }

    public void eatFood(SnakePart tail, Food food, SnakePart tailMemory) {
        if (tail.getX() == food.getX()
                && tail.getY() == food.getY()) {

            snakeGameViewModel.generateNewFood();

            snakeGameViewModel.getSnakeGameView().drawRectangle(settings.getDisplaySize(), settings.getDisplaySize(), tailMemory.getX(), tailMemory.getY(), Color.BLUE);

            score += 10;

            snake.getSnakeBody().add(new SnakePart(tailMemory.getX(), tailMemory.getY()));
        }
    }


    public void restartGame() {
        stopGame();
        startGame();
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void stopGame() {
        running = false;
        score = 0;
        snakeGameViewModel.stopDrawing();
    }

    public void startGame() {
        snake.setDirection(Direction.RIGHT);
        snake.getSnakeBody().add(new SnakePart( settings.getWorldSizeX()/2, settings.getWorldSizeY()/2));
        snakeGameViewModel.startDrawing();
        running = true;
    }

    public static void main(String[] args) throws Exception {
        Model model = new Model();
        SnakeGameViewModel snakeGameViewModel = new SnakeGameViewModel(model);
        SnakeGameView snakeGameView = new SnakeGameView(model, snakeGameViewModel);
        snakeGameView.start(new Stage());
    }
}
