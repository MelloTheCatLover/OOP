package ru.nsu.kozoliy;

public class Settings {
    private double difficulty;
    private int worldSizeX;
    private int worldSizeY;
    private int displaySize;
    private int foodCount;


    public Settings() {
    }

    public Settings(double difficulty, int worldSizeX, int worldSizeY, int displaySize, int foodCount) {
        this.difficulty = difficulty;
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
        this.displaySize = displaySize;
        this.foodCount = foodCount;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getWorldSizeX() {
        return worldSizeX;
    }

    public void setWorldSizeX(int worldSizeX) {
        this.worldSizeX = worldSizeX;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public int getWorldSizeY() {
        return worldSizeY;
    }

    public void setWorldSizeY(int worldSizeY) {
        this.worldSizeY = worldSizeY;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }
}
