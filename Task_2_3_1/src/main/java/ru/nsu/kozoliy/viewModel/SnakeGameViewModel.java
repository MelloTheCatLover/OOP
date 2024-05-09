package ru.nsu.kozoliy.viewModel;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.nsu.kozoliy.Direction;
import ru.nsu.kozoliy.SnakeGame;
import ru.nsu.kozoliy.models.Food;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.SnakePart;
import ru.nsu.kozoliy.view.SnakeGameView;

import java.util.ArrayList;


public class SnakeGameViewModel {

    private Model model;

    public SnakeGameViewModel(Model model) {
        this.model = model;
    }


    private SnakeGameView snakeGameView = new SnakeGameView(model, this);


    public SnakeGameView getSnakeGameView() {
        return snakeGameView;
    }

    public void move(Node tailL, SnakePart tailMemory) {

        SnakePart tail = new SnakePart((int)tailL.getTranslateX(),(int)tailL.getTranslateY());

        model.detectCollision(tail);

        if (tail.getX() < 0) {
            tail.setX(model.getSettings().getWorldSizeX() - model.getSettings().getDisplaySize());
        } else if (tail.getX() >= model.getSettings().getWorldSizeX() ) {
            tail.setX(0);
        }

        if (tail.getY() < model.getSettings().getDisplaySize()) {
            tail.setY(model.getSettings().getWorldSizeY() - model.getSettings().getDisplaySize());
        } else if (tail.getY() >= model.getSettings().getWorldSizeY() ) {
            tail.setY(model.getSettings().getDisplaySize());
        }

        for (Object food : snakeGameView.getFoods().getChildren().toArray()) {
            model.eatFood(tail, (Food)food, tailMemory);
        }



    }


    public void generateNewFood(){
        Food food = new Food(0, 0);

        food.setX((int)(Math.random() * (model.getSettings().getWorldSizeX() - model.getSettings().getDisplaySize())) / model.getSettings().getDisplaySize() * model.getSettings().getDisplaySize());
        food.setY((int)(Math.random() * (model.getSettings().getWorldSizeY() - model.getSettings().getDisplaySize() - model.getSettings().getDisplaySize())) / model.getSettings().getDisplaySize() * model.getSettings().getDisplaySize() + model.getSettings().getDisplaySize());


        snakeGameView.drawRectangle(model.getSettings().getDisplaySize(), model.getSettings().getDisplaySize(), food.getX(), food.getY(), Color.RED);
    }



    public void stopDrawing() {
        snakeGameView.getSnakeDraw().getChildren().clear();

    }

    public void startDrawing() {
        snakeGameView.startTimeline();
    }
}


