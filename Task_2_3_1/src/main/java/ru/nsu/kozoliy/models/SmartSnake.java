package ru.nsu.kozoliy.models;

import java.util.ArrayList;

/**
 * Represents a smart snake in the snake game.
 */
public class SmartSnake {
    private ArrayList<SnakePart> snakeBody;
    private Direction direction;
    private final int displaySize;
    private final int worldSizeX;
    private final int worldSizeY;

    /**
     * Constructs a SmartSnake object with the specified parameters.
     *
     * @param startX      The initial X-coordinate of the snake's head.
     * @param startY      The initial Y-coordinate of the snake's head.
     * @param displaySize The size of each display unit (e.g., cell) in the game grid.
     * @param worldSizeX  The width of the game world.
     * @param worldSizeY  The height of the game world.
     */
    public SmartSnake(int startX, int startY, int displaySize, int worldSizeX, int worldSizeY) {
        this.displaySize = displaySize;
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
        this.direction = Direction.RIGHT;

        snakeBody = new ArrayList<>();
        snakeBody.add(new SnakePart(startX, startY));
        for (int i = 1; i < 3; i++) {
            getSnakeBody().add(new SnakePart(worldSizeX - i * displaySize, worldSizeY));
        }
    }

    /**
     * Gets the snake's body.
     *
     * @return ArrayList of SnakePart representing the snake's body parts.
     */
    public ArrayList<SnakePart> getSnakeBody() {
        return snakeBody;
    }

    /**
     * Sets the direction of the snake's movement.
     *
     * @param direction The new direction of the snake's movement.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets the direction of the snake's movement.
     *
     * @return The current direction of the snake's movement.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Moves the snake's head according to its current direction.
     */
    public void move() {
        SnakePart head = snakeBody.get(0);

        int newX = (int) head.getX();
        int newY = (int) head.getY();

        switch (direction) {
            case UP:
                newY -= displaySize;
                break;
            case DOWN:
                newY += displaySize;
                break;
            case LEFT:
                newX -= displaySize;
                break;
            case RIGHT:
                newX += displaySize;
                break;
        }

        if (newX < 0) {
            newX = worldSizeX - displaySize;
        } else if (newX >= worldSizeX) {
            newX = 0;
        }

        if (newY < 0) {
            newY = worldSizeY - displaySize;
        } else if (newY >= worldSizeY) {
            newY = 0;
        }

        snakeBody.add(0, new SnakePart(newX, newY));
        snakeBody.remove(snakeBody.size() - 1);
    }

    /**
     * Increases the length of the snake by adding a new body part.
     */
    public void grow() {
        SnakePart tail = snakeBody.get(snakeBody.size() - 1);
        snakeBody.add(new SnakePart((int) tail.getX(), (int) tail.getY()));
    }
}
