package ru.nsu.kozoliy.Entities;

import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;

/**
 * Класс, представляющий пиццу в пиццерии.
 */
public class Pizza {

    private int size; // Размер пиццы
    private PizzaType type; // Тип пиццы
    private boolean isCooked = false; // Признак приготовленности пиццы

    /**
     * Перечисление, представляющее возможные типы пиццы.
     */
    public enum PizzaType {
        Margarita,
        BBQ,
        FourCheeses,
        Diablo,
        Vegan
    }

    /**
     * Переопределенный метод toString для получения строкового представления пиццы.
     *
     * @return строковое представление пиццы
     */
    @ExcludeFromJacocoGeneratedReport
    @Override
    public String toString() {
        return "Pizza{" +
                "size='" + size + '\'' +
                ", type=" + type +
                ", isCooked=" + isCooked +
                '}';
    }

    /**
     * Метод для получения размера пиццы.
     *
     * @return размер пиццы
     */
    public int getSize() {
        return size;
    }

    /**
     * Метод для установки размера пиццы.
     *
     * @param size размер пиццы
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Метод для получения типа пиццы.
     *
     * @return тип пиццы
     */
    public PizzaType getType() {
        return type;
    }

    /**
     * Конструктор для создания экземпляра пиццы с заданным размером и типом.
     *
     * @param size размер пиццы
     * @param type тип пиццы
     */
    public Pizza(int size, PizzaType type) {
        this.size = size;
        this.type = type;
    }

    /**
     * Метод для установки признака приготовленности пиццы.
     *
     * @param cooked признак приготовленности пиццы
     */
    public void setCooked(boolean cooked) {
        isCooked = cooked;
    }
}
