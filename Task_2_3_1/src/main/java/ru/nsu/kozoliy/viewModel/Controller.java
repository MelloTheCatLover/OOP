package ru.nsu.kozoliy.viewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nsu.kozoliy.ExcludeFromJacocoGeneratedReport;
import ru.nsu.kozoliy.models.Settings;
import ru.nsu.kozoliy.models.Model;
import ru.nsu.kozoliy.view.SnakeGameView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class responsible for handling user interactions with the Snake game settings UI.
 */
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

    private final String[] worldSizes = {"Small", "Medium", "Large", "Huge"};

    private final String[] speed = {"Easy", "Normal", "Hard", "Impossible"};

    private final String[] cellSize = {"Small", "Normal", "Large"};

    private final Settings settings = new Settings();


    /**
     * Initializes the controller with the initial values and event handlers.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    @ExcludeFromJacocoGeneratedReport
    public void initialize(URL location, ResourceBundle resources) {
        cellSizeChoiceBox.getItems().addAll(cellSize);
        cellSizeChoiceBox.setOnAction(this::getCellSize);

        speedChoiceBox.getItems().addAll(speed);
        speedChoiceBox.setOnAction(this::getSpeed);

        worldSizeChoiceBox.getItems().addAll(worldSizes);
        worldSizeChoiceBox.setOnAction(this::getWorldSize);
    }

    /**
     * Handles the action event when the user submits the settings.
     *
     * @param event The action event.
     */
    @ExcludeFromJacocoGeneratedReport
    public void submit(ActionEvent event) {
        settings.setFoodCount(Integer.parseInt(foodCount.getText()));

        try {
            Model model = new Model();
            model.setSettings(settings); // Set the settings
            model.setMoved(false);
            model.setRunning(false);
            SnakeGameViewModel snakeGameViewModel = new SnakeGameViewModel(model);
            SnakeGameView snakeGameView = new SnakeGameView(model, snakeGameViewModel, settings);
            snakeGameView.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (settings.getFoodCount() == 0 || settings.getDisplaySize() == 0 || settings.getWorldSizeY() == 0) {
            warningLabel.setVisible(true);
        }
        ((Stage) startButton.getScene().getWindow()).close();
    }

    /**
     * Sets the speed setting based on the user's choice.
     *
     * @param event The action event.
     */
    @ExcludeFromJacocoGeneratedReport
    public void getSpeed(ActionEvent event) {
        String speed = speedChoiceBox.getValue();
        switch (speed) {
            case "Easy":
                settings.setDifficulty(0.4);
                break;
            case "Normal":
                settings.setDifficulty(0.2);
                break;
            case "Hard":
                settings.setDifficulty(0.07);
                break;
            case "Impossible":
                settings.setDifficulty(0.01);
                break;
            default:
                // Handle cases when the value doesn't match any option
                break;
        }
    }

    /**
     * Sets the cell size setting based on the user's choice.
     *
     * @param event The action event.
     */
    @ExcludeFromJacocoGeneratedReport
    public void getCellSize(ActionEvent event) {
        String cellSize = cellSizeChoiceBox.getValue();
        switch (cellSize) {
            case "Small":
                settings.setDisplaySize(10);
                break;
            case "Normal":
                settings.setDisplaySize(20);
                break;
            case "Large":
                settings.setDisplaySize(50);
                break;
            default:
                // Handle cases when the value doesn't match any option
                break;
        }
    }

    /**
     * Sets the world size setting based on the user's choice.
     *
     * @param event The action event.
     */
    @ExcludeFromJacocoGeneratedReport
    public void getWorldSize(ActionEvent event) {
        String worldSize = worldSizeChoiceBox.getValue();
        switch (worldSize) {
            case "Small":
                settings.setWorldSizeX(600);
                settings.setWorldSizeY(400);
                break;
            case "Medium":
                settings.setWorldSizeX(800);
                settings.setWorldSizeY(600);
                break;
            case "Large":
                settings.setWorldSizeX(1000);
                settings.setWorldSizeY(800);
                break;
            case "Huge":
                settings.setWorldSizeX(1800);
                settings.setWorldSizeY(1000);
                break;
            default:
                // Handle cases when the value doesn't match any option
                break;
        }
    }
}
