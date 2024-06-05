package ru.nsu.kozoliy.models;

import javafx.scene.paint.Color;
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.viewmodel.SnakeGameViewModel;

/**
 * Represents the game model.
 */
public class Model {

    /** The view model associated with the model. */
    SnakeGameViewModel snakeGameViewModel = new SnakeGameViewModel(this);

    /** Indicates whether the snake has moved. */
    private boolean moved = false;

    /** Indicates whether the game is running. */
    private boolean running = false;

    /** The game settings. */
    private Settings settings = new Settings(0.1, 800, 600, 20, 5);

    /** The snake object. */
    private Snake snake;

    /** The current score. */
    private int score = 0;

    /**
     * Sets whether the snake has moved.
     *
     * @param moved True if the snake has moved, false otherwise.
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     * Checks if the snake has moved.
     *
     * @return True if the snake has moved, false otherwise.
     */
    public boolean isMoved() {
        return moved;
    }

    /**
     * Checks if the game is running.
     *
     * @return True if the game is running, false otherwise.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets settings.
     *
     * @param settings the game settings.
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }


    /**
     * Sets running.
     *
     * @param running Running param.
     */
    @ExcludeFromJacocoGeneratedReport
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Gets the game settings.
     *
     * @return The game settings.
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Gets the snake object.
     *
     * @return The snake object.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Sets the snake object.
     *
     * @param snake The snake object to set.
     */
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    /**
     * Gets the current score.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the current score.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Detects collision between the snake and its parts.
     *
     * @param tail The tail of the snake.
     */
    @ExcludeFromJacocoGeneratedReport
    public void detectCollision(SnakePart tail) {
        for (SnakePart part : snake.getSnakeBody()) {
            if (part != tail && tail.getX() == part.getX()
                    && tail.getY() == part.getY()) {
                restartGame();
                break;
            }
        }
    }

    /**
     * Handles the snake eating food.
     *
     * @param tail       The tail of the snake.
     * @param food       The food object.
     * @param tailMemory The memory of the snake's tail.
     */
    @ExcludeFromJacocoGeneratedReport
    public void eatFood(SnakePart tail, Food food, SnakePart tailMemory) {
        if (tail.getX() == food.getX() && tail.getY() == food.getY()) {
            snakeGameViewModel.generateNewFood();
            snakeGameViewModel.getSnakeGameView().drawRectangle(
                    settings.getDisplaySize(),
                    settings.getDisplaySize(),
                    tailMemory.getX(),
                    tailMemory.getY(),
                    Color.BLUE);
            score += 10;
            snake.getSnakeBody().add(
                    new SnakePart(tailMemory.getX(), tailMemory.getY()));
        }
    }

    /**
     * Restarts the game.
     */
    public void restartGame() {
        stopGame();
        startGame();
    }

    /**
     * Stops the game.
     */
    public void stopGame() {
        running = false;
        score = 0;
        snakeGameViewModel.stopDrawing();
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        snake = new Snake(new SnakePart(settings.getWorldSizeX() / 2,
                settings.getWorldSizeY() / 2),
                Direction.RIGHT);
        snakeGameViewModel.startDrawing();
        running = true;
    }
}
