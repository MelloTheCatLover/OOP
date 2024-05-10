package ru.nsu.kozoliy;

import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.nsu.kozoliy.models.Direction;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.Snake;
import ru.nsu.kozoliy.models.SnakePart;
import ru.nsu.kozoliy.view.SnakeGameView;
import ru.nsu.kozoliy.viewModel.SnakeGameViewModel;
import static org.junit.jupiter.api.Assertions.*;

class SnakeGameViewTest {

    private Model model;
    private SnakeGameViewModel viewModel;
    private SnakeGameView view;

    @BeforeEach
    void setUp() {
        model = new Model();
        viewModel = new SnakeGameViewModel(model);
        view = new SnakeGameView(model, viewModel, model.getSettings());
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


}
