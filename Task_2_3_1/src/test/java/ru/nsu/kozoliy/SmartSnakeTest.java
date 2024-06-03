package ru.nsu.kozoliy;

import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.models.Direction;
import ru.nsu.kozoliy.models.SmartSnake;
import ru.nsu.kozoliy.models.SnakePart;

import static org.junit.jupiter.api.Assertions.*;

class SmartSnakeTest {

    @Test
    void testInitialSnakeBody() {
        // Given
        int startX = 100;
        int startY = 200;
        int displaySize = 20;
        int worldSizeX = 500;
        int worldSizeY = 400;

        // When
        SmartSnake smartSnake = new SmartSnake(startX, startY, displaySize, worldSizeX, worldSizeY);

        // Then
        assertEquals(3, smartSnake.getSnakeBody().size());
        assertEquals(startX, smartSnake.getSnakeBody().get(0).getX());
        assertEquals(startY, smartSnake.getSnakeBody().get(0).getY());
        assertEquals(worldSizeX - displaySize, smartSnake.getSnakeBody().get(1).getX());
        assertEquals(worldSizeY, smartSnake.getSnakeBody().get(1).getY());
        assertEquals(worldSizeX - 2 * displaySize, smartSnake.getSnakeBody().get(2).getX());
        assertEquals(worldSizeY, smartSnake.getSnakeBody().get(2).getY());
    }

    @Test
    void testSetDirection() {
        // Given
        int startX = 100;
        int startY = 200;
        int displaySize = 20;
        int worldSizeX = 500;
        int worldSizeY = 400;
        SmartSnake smartSnake = new SmartSnake(startX, startY, displaySize, worldSizeX, worldSizeY);

        // When
        smartSnake.setDirection(Direction.LEFT);

        // Then
        assertEquals(Direction.LEFT, smartSnake.getDirection());
    }

    @Test
    void testMove() {
        // Given
        int startX = 100;
        int startY = 200;
        int displaySize = 20;
        int worldSizeX = 500;
        int worldSizeY = 400;
        SmartSnake smartSnake = new SmartSnake(startX, startY, displaySize, worldSizeX, worldSizeY);
        Direction direction = smartSnake.getDirection();

        // When
        smartSnake.move();

        // Then
        assertEquals(120, smartSnake.getSnakeBody().get(0).getX());
        assertEquals(200, smartSnake.getSnakeBody().get(0).getY());
    }

    @Test
    void testGrow() {
        // Given
        int startX = 100;
        int startY = 200;
        int displaySize = 20;
        int worldSizeX = 500;
        int worldSizeY = 400;
        SmartSnake smartSnake = new SmartSnake(startX, startY, displaySize, worldSizeX, worldSizeY);
        int oldLength = smartSnake.getSnakeBody().size();

        // When
        smartSnake.grow();

        // Then
        assertEquals(oldLength + 1, smartSnake.getSnakeBody().size());
    }
}
