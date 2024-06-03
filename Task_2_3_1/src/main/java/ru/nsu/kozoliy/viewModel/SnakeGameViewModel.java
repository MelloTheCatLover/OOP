package ru.nsu.kozoliy.viewModel;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.models.Settings;
import ru.nsu.kozoliy.models.Food;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.SnakePart;
import ru.nsu.kozoliy.view.SnakeGameView;

/**
 * ViewModel class responsible for managing the logic behind the Snake game view.
 */
public class SnakeGameViewModel {

    private final Model model;
    private final SnakeGameView snakeGameView;


    /**
     * Constructs a SnakeGameViewModel object with the specified model.
     *
     * @param model The model representing the state of the Snake game.
     */
    public SnakeGameViewModel(Model model) {
        this.model = model;
        this.snakeGameView = new SnakeGameView(model, new Settings(0.1, 800, 600, 20, 5));
    }

    /**
     * Retrieves the SnakeGameView associated with this view model.
     *
     * @return The SnakeGameView.
     */
    @ExcludeFromJacocoGeneratedReport
    public SnakeGameView getSnakeGameView() {
        return snakeGameView;
    }

    /**
     * Moves the snake and handles collision detection.
     *
     * @param tailL      The tail node of the snake.
     * @param tailMemory The memory of the tail position before the move.
     */
    @ExcludeFromJacocoGeneratedReport
    public void move(Node tailL, SnakePart tailMemory) {
        SnakePart tail = new SnakePart((int) tailL.getTranslateX(), (int) tailL.getTranslateY());
        model.detectCollision(tail);

        if (tail.getX() < 0) {
            tail.setX(model.getSettings().getWorldSizeX() - model.getSettings().getDisplaySize());
        } else if (tail.getX() >= model.getSettings().getWorldSizeX()) {
            tail.setX(0);
        }

        if (tail.getY() < model.getSettings().getDisplaySize()) {
            tail.setY(model.getSettings().getWorldSizeY() - model.getSettings().getDisplaySize());
        } else if (tail.getY() >= model.getSettings().getWorldSizeY()) {
            tail.setY(model.getSettings().getDisplaySize());
        }

        for (Object food : snakeGameView.getFoods().getChildren().toArray()) {
            model.eatFood(tail, (Food) food, tailMemory);
        }
    }

    /**
     * Generates a new food item in the game world.
     */
    @ExcludeFromJacocoGeneratedReport
    public void generateNewFood() {
        Food food = new Food(0, 0);

        food.setX((int) (Math.random() * (model.getSettings().getWorldSizeX() - model.getSettings().getDisplaySize())) / model.getSettings().getDisplaySize() * model.getSettings().getDisplaySize());
        food.setY((int) (Math.random() * (model.getSettings().getWorldSizeY() - model.getSettings().getDisplaySize() - model.getSettings().getDisplaySize())) / model.getSettings().getDisplaySize() * model.getSettings().getDisplaySize() + model.getSettings().getDisplaySize());

        snakeGameView.drawRectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize(), food.getX(), food.getY(), Color.RED);
    }

    /**
     * Clears the drawing of the snake.
     */
    @ExcludeFromJacocoGeneratedReport
    public void stopDrawing() {
        snakeGameView.getSnakeDraw().getChildren().clear();
    }

    /**
     * Starts drawing the snake and initializes the game.
     */
    public void startDrawing() {
        snakeGameView.startTimeline();
        snakeGameView.startGameDraw();
    }
}
