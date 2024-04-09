package ru.nsu.kozoliy.Entities;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;

public class Pizza {

    private int size;

    private PizzaType type;

    private boolean isCooked = false;

    public enum PizzaType {
        Margarita,
        BBQ,
        FourCheeses,
        Diablo,
        Vegan
    }

    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Pizza{" +
                "size='" + size + '\'' +
                ", type=" + type +
                ", isCooked=" + isCooked +
                '}';
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public PizzaType getType() {
        return type;
    }

    public Pizza(int size, PizzaType type) {
        this.size = size;
        this.type = type;
    }

/*

    public void setType(PizzaType type) {
        this.type = type;
    }

    public boolean isCooked() {
        return isCooked;
    }
 */



    public void setCooked(boolean cooked) {
        isCooked = cooked;
    }
}
