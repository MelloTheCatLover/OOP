package ru.nsu.kozoliy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestSnake {

    @Test
    void testSnake() {
        Snake snake = new Snake();
        assertNotEquals(snake.settings, new Settings(0.1, 800, 600, 20, 5));
    }
}
