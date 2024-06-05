package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.models.Food;




class FoodTest {

    private Food food;

    @BeforeEach
    void setUp() {
        food = new Food(10, 20);
    }

    @Test
    void getX() {
        assertEquals(10, food.getX());
    }

    @Test
    void setX() {
        food.setX(30);
        assertEquals(30, food.getX());
    }

    @Test
    void getY() {
        assertEquals(20, food.getY());
    }

    @Test
    void setY() {
        food.setY(40);
        assertEquals(40, food.getY());
    }
}
