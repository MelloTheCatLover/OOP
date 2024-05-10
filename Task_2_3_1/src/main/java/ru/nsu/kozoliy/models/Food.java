package ru.nsu.kozoliy.models;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;

/**
 * Represents a food item in the game.
 */
public class Food {
    private int xCoordinate;
    private int yCoordinate;

    /**
     * Gets the x-coordinate of the food.
     *
     * @return The x-coordinate of the food.
     */
    @ExcludeFromJacocoGeneratedReport
    public int getX() {
        return xCoordinate;
    }

    /**
     * Sets the x-coordinate of the food.
     *
     * @param x The x-coordinate to set.
     */
    @ExcludeFromJacocoGeneratedReport
    public void setX(int x) {
        this.xCoordinate = x;
    }

    /**
     * Gets the y-coordinate of the food.
     *
     * @return The y-coordinate of the food.
     */
    @ExcludeFromJacocoGeneratedReport
    public int getY() {
        return yCoordinate;
    }

    /**
     * Sets the y-coordinate of the food.
     *
     * @param y The y-coordinate to set.
     */
    @ExcludeFromJacocoGeneratedReport
    public void setY(int y) {
        this.yCoordinate = y;
    }

    /**
     * Constructs a new Food object with the specified coordinates.
     *
     * @param x The x-coordinate of the food.
     * @param y The y-coordinate of the food.
     */

    public Food(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }
}
