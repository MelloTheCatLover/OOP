package ru.nsu.kozoliy.models;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;

import java.util.ArrayList;

/**
 * Represents the snake in the game.
 */
public class Snake {

    /** The direction of the snake. */
    private Direction direction;

    /** The body parts of the snake. */
    private ArrayList<SnakePart> snakeBody = new ArrayList<>();

    /**
     * Constructs a snake with the specified head and direction.
     *
     * @param head      The head of the snake.
     * @param direction The initial direction of the snake.
     */
    public Snake(SnakePart head, Direction direction) {
        this.snakeBody.add(head);
        this.direction = direction;
    }

    /**
     * Constructs a snake with the specified coordinates and direction.
     *
     * @param x         The x-coordinate of the snake's head.
     * @param y         The y-coordinate of the snake's head.
     * @param direction The initial direction of the snake.
     */
    public Snake(int x, int y, Direction direction) {
        SnakePart head = new SnakePart(x, y);
        this.snakeBody.add(head);
        this.direction = direction;
    }

    /**
     * Gets the size of the snake.
     *
     * @return The size of the snake.
     */
    @ExcludeFromJacocoGeneratedReport
    public int getSize() {
        return snakeBody.size();
    }

    /**
     * Gets the body parts of the snake.
     *
     * @return The body parts of the snake.
     */
    @ExcludeFromJacocoGeneratedReport
    public ArrayList<SnakePart> getSnakeBody() {
        return snakeBody;
    }

    /**
     * Gets the direction of the snake.
     *
     * @return The direction of the snake.
     */
    @ExcludeFromJacocoGeneratedReport
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the snake.
     *
     * @param direction The direction to set.
     */
    @ExcludeFromJacocoGeneratedReport
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
