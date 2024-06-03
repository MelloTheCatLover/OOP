package ru.nsu.kozoliy.models;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;

/**
 * Represents a part of the snake.
 */
public class SnakePart {
    /** The x-coordinate of the snake part. */
    private int xcoordinate;

    /** The y-coordinate of the snake part. */
    private int ycoordinate;

    /**
     * Constructs a snake part with the specified coordinates.
     *
     * @param x The x-coordinate of the snake part.
     * @param y The y-coordinate of the snake part.
     */
    public SnakePart(int x, int y) {
        this.xcoordinate = x;
        this.ycoordinate = y;
    }

    /**
     * Sets the x-coordinate of the snake part.
     *
     * @param x The x-coordinate to set.
     */
    public void setX(int x) {
        this.xcoordinate = x;
    }

    /**
     * Sets the y-coordinate of the snake part.
     *
     * @param y The y-coordinate to set.
     */
    public void setY(int y) {
        this.ycoordinate = y;
    }

    /**
     * Gets the x-coordinate of the snake part.
     *
     * @return The x-coordinate of the snake part.
     */
    public int getX() {
        return xcoordinate;
    }

    /**
     * Gets the y-coordinate of the snake part.
     *
     * @return The y-coordinate of the snake part.
     */
    public int getY() {
        return ycoordinate;
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
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SnakePart snakePart = (SnakePart) obj;
        return xcoordinate == snakePart.xcoordinate && ycoordinate == snakePart.ycoordinate;
    }

    /**
     * Calculates the hash code of this snake part.
     *
     * @return The hash code of this snake part.
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public int hashCode() {
        return 31 * xcoordinate + ycoordinate;
    }
}
