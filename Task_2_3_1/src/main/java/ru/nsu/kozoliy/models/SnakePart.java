package ru.nsu.kozoliy.models;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;

/**
 * Represents a part of the snake.
 */
public class SnakePart {
    /** The x-coordinate of the snake part. */
    private int xCoordinate;

    /** The y-coordinate of the snake part. */
    private int yCoordinate;

    /**
     * Constructs a snake part with the specified coordinates.
     *
     * @param x The x-coordinate of the snake part.
     * @param y The y-coordinate of the snake part.
     */
    public SnakePart(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    /**
     * Sets the x-coordinate of the snake part.
     *
     * @param x The x-coordinate to set.
     */
    public void setX(int x) {
        this.xCoordinate = x;
    }

    /**
     * Sets the y-coordinate of the snake part.
     *
     * @param y The y-coordinate to set.
     */
    public void setY(int y) {
        this.yCoordinate = y;
    }

    /**
     * Gets the x-coordinate of the snake part.
     *
     * @return The x-coordinate of the snake part.
     */
    public int getX() {
        return xCoordinate;
    }

    /**
     * Gets the y-coordinate of the snake part.
     *
     * @return The y-coordinate of the snake part.
     */
    public int getY() {
        return yCoordinate;
    }

    /**
     * Checks if this snake part is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    @ExcludeFromJacocoGeneratedReport
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SnakePart snakePart = (SnakePart) obj;
        return xCoordinate == snakePart.xCoordinate && yCoordinate == snakePart.yCoordinate;
    }

    /**
     * Calculates the hash code of this snake part.
     *
     * @return The hash code of this snake part.
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public int hashCode() {
        return 31 * xCoordinate + yCoordinate;
    }
}
