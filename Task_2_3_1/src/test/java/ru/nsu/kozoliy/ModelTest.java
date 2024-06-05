package ru.nsu.kozoliy;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.models.Direction;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.Settings;
import ru.nsu.kozoliy.models.Snake;
import ru.nsu.kozoliy.models.SnakePart;


class ModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
    }

    @Test
    void setMoved() {
        model.setMoved(true);
        assertTrue(model.isMoved());
    }

    @Test
    void isRunning() {
        assertFalse(model.isRunning());
    }

    @Test
    void setSettings() {
        Settings settings = new Settings(0.2, 600, 400, 15, 3);
        model.setSettings(settings);
        assertEquals(settings, model.getSettings());
    }

    @Test
    void setSnake() {
        Snake snake = new Snake(new SnakePart(100, 100), Direction.UP);
        model.setSnake(snake);
        assertEquals(snake, model.getSnake());
    }

    @Test
    void setAndGetScore() {
        model.setScore(50);
        assertEquals(50, model.getScore());
    }

    @Test
    void restartGame() {
        model.restartGame();
        assertTrue(model.isRunning());
        assertEquals(0, model.getScore());
    }

    @Test
    void stopGame() {
        model.stopGame();
        assertFalse(model.isRunning());
        assertEquals(0, model.getScore());
    }

    @Test
    void startGame() {
        model.startGame();
        assertTrue(model.isRunning());
        assertNotNull(model.getSnake());
    }
}
