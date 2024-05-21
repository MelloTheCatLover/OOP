package ru.nsu.kozoliy.models;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a bad snake in the game.
 * The bad snake moves randomly within the game world and has a fixed length of 2 segments.
 */
public class BadSnake {
    private ArrayList<SnakePart> snakeBody;
    private Direction direction;
    private final int displaySize;
    private final int worldSizeX;
    private final int worldSizeY;
    private final Random random = new Random();

    /**
     * Constructs a new BadSnake with the specified parameters.
     *
     * @param startX      the initial x-coordinate of the snake's head.
     * @param startY      the initial y-coordinate of the snake's head.
     * @param displaySize the size of each snake segment.
     * @param worldSizeX  the width of the game world.
     * @param worldSizeY  the height of the game world.
     */
    public BadSnake(int startX, int startY, int displaySize, int worldSizeX, int worldSizeY) {
        this.snakeBody = new ArrayList<>();
        this.snakeBody.add(new SnakePart(startX, startY));
        this.direction = Direction.values()[random.nextInt(Direction.values().length)];
        this.displaySize = displaySize;
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
        for (int i = 1; i < 2; i++) {
            getSnakeBody().add(new SnakePart(worldSizeX - i * displaySize, worldSizeY));
        }
    }

    /**
     * Returns the body of the snake.
     *
     * @return the list of SnakePart objects representing the snake's body.
     */
    public ArrayList<SnakePart> getSnakeBody() {
        return snakeBody;
    }

    /**
     * Returns the current direction of the snake.
     *
     * @return the current direction of the snake.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Changes the direction of the snake to a new random direction.
     * The new direction cannot be the direct opposite of the current direction.
     */
    public void changeDirection() {
        Direction oldDirection = getDirection();
        Direction newDirection = Direction.values()[random.nextInt(Direction.values().length)];

        if ((oldDirection == Direction.UP && newDirection == Direction.DOWN) ||
                (oldDirection == Direction.DOWN && newDirection == Direction.UP) ||
                (oldDirection == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (oldDirection == Direction.RIGHT && newDirection == Direction.LEFT)) {
            this.direction = oldDirection;
        } else {
            this.direction = newDirection;
        }
    }

    /**
     * Moves the snake in its current direction.
     * If the snake reaches the boundary of the game world, it wraps around to the opposite side.
     */
    public void move() {
        ArrayList<SnakePart> body = getSnakeBody();
        int oldLen = body.size();
        SnakePart head = body.get(0);

        // Calculate the new position of the head based on the direction
        int newX = head.getX();
        int newY = head.getY();

        switch (getDirection()) {
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

        // Wrap around logic
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

        // Add the new head position
        body.add(0, new SnakePart(newX, newY));

        // Remove the last part to maintain the length of 2
        while (body.size() > oldLen) {
            body.remove(body.size() - 1);
        }
    }
}
