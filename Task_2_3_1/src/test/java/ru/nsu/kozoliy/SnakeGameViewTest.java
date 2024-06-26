package ru.nsu.kozoliy;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.models.Direction;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.Snake;
import ru.nsu.kozoliy.models.SnakePart;
import ru.nsu.kozoliy.view.SnakeGameView;
import ru.nsu.kozoliy.viewmodel.Controller;



class SnakeGameViewTest {

    private Model model;
    private SnakeGameView view;

    @BeforeEach
    void setUp() {
        model = new Model();
        view = new SnakeGameView(model, model.getSettings());
    }

    @Test
    void drawSnake() {
        Snake snake = new Snake(new SnakePart(50, 50), Direction.RIGHT);
        model.setSnake(snake);
        assertEquals(1, view.drawSnake(snake.getSnakeBody()).size());
    }


    @Test
    void generateFood() {
        assertEquals(model.getSettings().getFoodCount(), view.generateFood().size());
    }

    @Test
    void detectCollision() {
        Snake snake = new Snake(new SnakePart(50, 50), Direction.RIGHT);
        model.setSnake(snake);
        assertEquals(0, model.getScore());
        Rectangle tmp = new Rectangle(10, 10); // Примерные размеры
        tmp.setTranslateX(50);
        tmp.setTranslateY(50);
        view.detectCollision(tmp);
        assertEquals(0, model.getScore());
    }

    @Test
    void eatFood() {
        Snake snake = new Snake(new SnakePart(50, 50), Direction.RIGHT);
        model.setSnake(snake);
        assertEquals(0, model.getScore());
        Node tmp = new Rectangle(10, 10);
        tmp.setTranslateX(snake.getSnakeBody().get(0).getX());
        tmp.setTranslateY(snake.getSnakeBody().get(0).getY());
        view.eatFood(tmp, new Rectangle(50, 50), new SnakePart(50, 50));
        assertEquals(0, model.getScore());
    }

    @Test
    void testController() {
        Controller cntr = new Controller();

        assertNotNull(cntr);

    }




}
