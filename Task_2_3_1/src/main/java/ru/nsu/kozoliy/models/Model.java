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
        snake = new Snake(new SnakePart( settings.getWorldSizeX()/2, settings.getWorldSizeY()/2), Direction.RIGHT);
        snakeGameViewModel.startDrawing();
        running = true;
    }

    public static void main(String[] args) throws Exception {
        Model model = new Model();
        SnakeGameViewModel snakeGameViewModel = new SnakeGameViewModel(model);
        SnakeGameView snakeGameView = new SnakeGameView(model, snakeGameViewModel);
        snakeGameView.start(new Stage());
    }

    public SnakePart changeDirection(SnakePart tail) {
        switch (snake.getDirection()) {
            case UP:
                tail.setX(snake.getSnakeBody().get(0).getX());
                tail.setY(snake.getSnakeBody().get(0).getY() - settings.getDisplaySize());
                break;
            case DOWN:
                tail.setX(snake.getSnakeBody().get(0).getX());
                tail.setY(snake.getSnakeBody().get(0).getY() + settings.getDisplaySize());
                break;
            case LEFT:
                tail.setX(snake.getSnakeBody().get(0).getX() - settings.getDisplaySize());
                tail.setY(snake.getSnakeBody().get(0).getY());
                break;
            case RIGHT:
                tail.setX(snake.getSnakeBody().get(0).getX() + settings.getDisplaySize());
                tail.setY(snake.getSnakeBody().get(0).getY());
                break;
        }
        return tail;
    }
}
