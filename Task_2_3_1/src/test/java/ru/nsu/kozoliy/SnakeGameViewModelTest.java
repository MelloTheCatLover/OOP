package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.kozoliy.models.*;
import ru.nsu.kozoliy.view.SnakeGameView;
import ru.nsu.kozoliy.viewModel.SnakeGameViewModel;



public class SnakeGameViewModelTest {

    private SnakeGameViewModel viewModel;

    private Model model;
    @BeforeEach
    public void setUp() {
        model = new Model();
        viewModel = new SnakeGameViewModel(model);
    }

    @Test
    public void testMove() {
        model.setSnake(new Snake(20, 20, Direction.UP));
        SnakePart tailMemory = new SnakePart(100, 100);

        Node tmp = new Rectangle(10,10);
        tmp.setTranslateY(200);
        tmp.setTranslateX(200);
        viewModel.move(tmp, tailMemory);
        assertTrue(viewModel.getSnakeGameView().getFoods().getChildren().isEmpty());


        Node tmp2 = new Rectangle(10,10);
        tmp2.setTranslateY(0);
        tmp2.setTranslateX(100);
        viewModel.move(tmp2, tailMemory);
        assertTrue(viewModel.getSnakeGameView().getFoods().getChildren().isEmpty());

    }



    @Test
    public void testStartDrawing() {
        // Проверяем, что метод запуска рисования змеи работает корректно
        viewModel.startDrawing();
        assertFalse(viewModel.getSnakeGameView().getSnakeDraw().getChildren().isEmpty()); // Проверяем, что рисунок змеи создан
    }
}
