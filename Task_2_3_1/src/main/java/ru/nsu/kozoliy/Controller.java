package ru.nsu.kozoliy;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
            Snake snake = new Snake();
            snake.setSettings(settings);
            snake.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Здесь можно добавить код для закрытия стартового окна, если это необходимо
        ((Stage) startButton.getScene().getWindow()).close();
    }

    public void getSpeed(ActionEvent event) {
        String speed = speedChoiceBox.getValue();
        switch (speed) {
            case "Легкая" -> {
                settings.setDifficulty(0.4);
            }
            case "Нормальная" -> {
                settings.setDifficulty(0.2);
            }
            case "Сложная" -> {
                settings.setDifficulty(0.07);
            }
            case "НЕВОЗМОЖНО" -> {
                settings.setDifficulty(0.01);
            }
        }
    }

    public void getCellSize(ActionEvent event) {
        String cellSize = cellSizeChoiceBox.getValue();
        switch (cellSize) {
            case "Маленькая" -> {
                settings.setDisplaySize(10);
            }
            case "Нормальная" -> {
                settings.setDisplaySize(20);
            }
            case "Большая" -> {
                settings.setDisplaySize(50);
            }
        }
    }

    public void getWorldSize(ActionEvent event) {
        String worldSize = worldSizeChoiceBox.getValue();
        switch (worldSize) {
            case "Маленький" -> {
                settings.setWorldSizeX(600);
                settings.setWorldSizeY(400);
            }
            case "Средний" -> {
                settings.setWorldSizeX(800);
                settings.setWorldSizeY(600);
            }
            case "Большшой>" -> {
                settings.setWorldSizeX(1000);
                settings.setWorldSizeY(800);
            }
            case "Огромный" -> {
                settings.setWorldSizeX(1920);
                settings.setWorldSizeY(1080);
            }
        }
    }
}
