package ru.nsu.kozoliy.models;

/**
 * Class representing settings for the Snake game.
 */
public class Settings {
    private double difficulty;
    private int worldSizeX;
    private int worldSizeY;
    private int displaySize;
    private int foodCount;

    /**
     * Constructs a Settings object with default values.
     */
    public Settings() {
    }

    /**
     * Constructs a Settings object with specified values.
     *
     * @param difficulty  The difficulty of the game.
     * @param worldSizeX  The width of the game world.
     * @param worldSizeY  The height of the game world.
     * @param displaySize The size of the display cells.
     * @param foodCount   The initial count of food items in the game.
     */
    public Settings(double difficulty, int worldSizeX, int worldSizeY, int displaySize, int foodCount) {
        this.difficulty = difficulty;
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
        this.displaySize = displaySize;
        this.foodCount = foodCount;
    }

    /**
     * Gets the count of food items in the game.
     *
     * @return The count of food items.
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * Sets the count of food items in the game.
     *
     * @param foodCount The count of food items.
     */
    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    /**
     * Gets the difficulty of the game.
     *
     * @return The difficulty of the game.
     */
    public double getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty of the game.
     *
     * @param difficulty The difficulty of the game.
     */
    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the width of the game world.
     *
     * @return The width of the game world.
     */
    public int getWorldSizeX() {
        return worldSizeX;
    }

    /**
     * Sets the width of the game world.
     *
     * @param worldSizeX The width of the game world.
     */
    public void setWorldSizeX(int worldSizeX) {
        this.worldSizeX = worldSizeX;
    }

    /**
     * Gets the size of the display cells.
     *
     * @return The size of the display cells.
     */
    public int getDisplaySize() {
        return displaySize;
    }

    /**
     * Gets the height of the game world.
     *
     * @return The height of the game world.
     */
    public int getWorldSizeY() {
        return worldSizeY;
    }

    /**
     * Sets the height of the game world.
     *
     * @param worldSizeY The height of the game world.
     */
    public void setWorldSizeY(int worldSizeY) {
        this.worldSizeY = worldSizeY;
    }

    /**
     * Sets the size of the display cells.
     *
     * @param displaySize The size of the display cells.
     */
    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }
}
