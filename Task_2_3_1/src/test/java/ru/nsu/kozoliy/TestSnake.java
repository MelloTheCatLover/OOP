package ru.nsu.kozoliy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.models.Direction;
import ru.nsu.kozoliy.models.Snake;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnakeTest {

    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(10, 20, Direction.RIGHT);
    }

    @Test
    void getSize() {
        assertEquals(1, snake.getSize());
    }

    @Test
    void getSnakeBody() {
        assertEquals(10, snake.getSnakeBody().get(0).getX());
        assertEquals(20, snake.getSnakeBody().get(0).getY());
    }

    @Test
    void getDirection() {
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    void setDirection() {
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection());
    }
}
