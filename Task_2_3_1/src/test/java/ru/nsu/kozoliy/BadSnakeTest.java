package ru.nsu.kozoliy;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.models.BadSnake;
import ru.nsu.kozoliy.models.Direction;




class BadSnakeTest {

    @Test
    void testInitialSnakeBody() {
        // Given
        int startX = 100;
        int startY = 200;
        int displaySize = 20;
        int worldSizeX = 500;
        int worldSizeY = 400;

        // When
        BadSnake badSnake = new BadSnake(startX, startY, displaySize, worldSizeX, worldSizeY);

        // Then
        assertEquals(3, badSnake.getSnakeBody().size());
        assertEquals(startX, badSnake.getSnakeBody().get(0).getX());
        assertEquals(startY, badSnake.getSnakeBody().get(0).getY());
        assertEquals(worldSizeX - displaySize, badSnake.getSnakeBody().get(1).getX());
        assertEquals(worldSizeY, badSnake.getSnakeBody().get(1).getY());
    }

    @Test
    void testChangeDirection() {
        // Given
        int startX = 100;
        int startY = 200;
        int displaySize = 20;
        int worldSizeX = 500;
        int worldSizeY = 400;
        BadSnake badSnake = new BadSnake(startX, startY, displaySize, worldSizeX, worldSizeY);
        Direction oldDirection = badSnake.getDirection();
        badSnake.setDirection(Direction.RIGHT);
        badSnake.move();

        badSnake.setDirection(Direction.UP);
        badSnake.move();

        badSnake.setDirection(Direction.LEFT);
        badSnake.move();

        badSnake.setDirection(Direction.DOWN);
        badSnake.move();


        // When
        badSnake.changeDirection();

        // Then
        assertNotNull(badSnake.getDirection());
    }

    @Test
    void testMove() {
        // Given
        int startX = 100;
        int startY = 200;
        int displaySize = 20;
        int worldSizeX = 500;
        int worldSizeY = 400;
        BadSnake badSnake = new BadSnake(startX, startY, displaySize, worldSizeX, worldSizeY);
        int oldLen = badSnake.getSnakeBody().size();
        Direction direction = badSnake.getDirection();

        // When
        badSnake.move();

        // Then
        assertEquals(4, badSnake.getSnakeBody().size()); // Length should remain the same
        switch (direction) {
            case UP:
                assertEquals(startY - displaySize, badSnake.getSnakeBody().get(0).getY());
                break;
            case DOWN:
                assertEquals(startY + displaySize, badSnake.getSnakeBody().get(0).getY());
                break;
            case LEFT:
                assertEquals(startX - displaySize, badSnake.getSnakeBody().get(0).getX());
                break;
            case RIGHT:
                assertEquals(startX + displaySize, badSnake.getSnakeBody().get(0).getX());
                break;
            default:
                break;
        }
    }
}
