package ru.nsu.kozoliy;

public class Settings {
    private int difficulty;
    private int worldSize;
    private int displaySize;
    private int foodCount;


    public Settings() {
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(int worldSize) {
        this.worldSize = worldSize;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }
}
