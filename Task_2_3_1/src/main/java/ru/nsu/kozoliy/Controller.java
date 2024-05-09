package ru.nsu.kozoliy;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.models.Snake;
import ru.nsu.kozoliy.models.SnakePart;
import ru.nsu.kozoliy.view.SnakeGameView;
import ru.nsu.kozoliy.viewModel.SnakeGameViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField foodCount;

    @FXML
    private ChoiceBox<String> worldSizeChoiceBox;

    @FXML
    private ChoiceBox<String> speedChoiceBox;

    @FXML
    private ChoiceBox<String> cellSizeChoiceBox;

    @FXML
    private Button startButton;

    @FXML
    private Label warningLabel;

    private String[] worldSizes = {"Маленький", "Средний", "Большшой", "Огромный"};

    private String[] speed = {"Легкая", "Нормальная", "Сложная", "НЕВОЗМОЖНО"};

    private String[] cellSize = {"Маленькая", "Нормальная", "Большая"};

    private Settings settings = new Settings();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cellSizeChoiceBox.getItems().addAll(cellSize);
        cellSizeChoiceBox.setOnAction(this::getCellSize);


        speedChoiceBox.getItems().addAll(speed);
        speedChoiceBox.setOnAction(this::getSpeed);

        worldSizeChoiceBox.getItems().addAll(worldSizes);
        worldSizeChoiceBox.setOnAction(this::getWorldSize);


    }


    public void submit(ActionEvent event) {
        settings.setFoodCount(Integer.parseInt(foodCount.getText()));

        try {
            Model model = new Model();
            model.setSettings(settings);
            model.setMoved(false);
            model.setRunning(false);
            model.setSnake(new Snake(new SnakePart( settings.getWorldSizeX()/2, settings.getWorldSizeY()/2), Direction.RIGHT));
            SnakeGameViewModel snakeGameViewModel = new SnakeGameViewModel(model);
            SnakeGameView snakeGameView = new SnakeGameView(model, snakeGameViewModel);
            snakeGameView.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (settings.getFoodCount() == 0 || settings.getDisplaySize() == 0 || settings.getWorldSizeY() == 0) {
            warningLabel.setVisible(true);
        }
        ((Stage) startButton.getScene().getWindow()).close();
    }

    public void getSpeed(ActionEvent event) {
        String speed = speedChoiceBox.getValue();
        switch (speed) {
            case "Легкая":
                settings.setDifficulty(0.4);
                break;
            case "Нормальная":
                settings.setDifficulty(0.2);
                break;
            case "Сложная":
                settings.setDifficulty(0.07);
                break;
            case "НЕВОЗМОЖНО":
                settings.setDifficulty(0.01);
                break;
            default:
                // Обработка для случаев, когда значение не соответствует ни одному варианту
                break;
        }
    }

    public void getCellSize(ActionEvent event) {
        String cellSize = cellSizeChoiceBox.getValue();
        switch (cellSize) {
            case "Маленькая":
                settings.setDisplaySize(10);
                break;
            case "Нормальная":
                settings.setDisplaySize(20);
                break;
            case "Большая":
                settings.setDisplaySize(50);
                break;
            default:
                // Обработка для случаев, когда значение не соответствует ни одному варианту
                break;
        }
    }

    public void getWorldSize(ActionEvent event) {
        String worldSize = worldSizeChoiceBox.getValue();
        switch (worldSize) {
            case "Маленький":
                settings.setWorldSizeX(600);
                settings.setWorldSizeY(400);
                break;
            case "Средний":
                settings.setWorldSizeX(800);
                settings.setWorldSizeY(600);
                break;
            case "Большшой":
                settings.setWorldSizeX(1000);
                settings.setWorldSizeY(800);
                break;
            case "Огромный":
                settings.setWorldSizeX(1800);
                settings.setWorldSizeY(1000);
                break;
            default:
                // Обработка для случаев, когда значение не соответствует ни одному варианту
                break;
        }
    }

}
