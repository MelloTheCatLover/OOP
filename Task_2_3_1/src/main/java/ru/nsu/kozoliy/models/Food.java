package ru.nsu.kozoliy.models;

/**
 * Represents a food item in the game.
 */
public class Food {
    private int x;
    private int y;

    /**
     * Gets the x-coordinate of the food.
     *
     * @return The x-coordinate of the food.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the food.
     *
     * @param x The x-coordinate to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the food.
     *
     * @return The y-coordinate of the food.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the food.
     *
     * @param y The y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Constructs a new Food object with the specified coordinates.
     *
     * @param x The x-coordinate of the food.
     * @param y The y-coordinate of the food.
     */
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
