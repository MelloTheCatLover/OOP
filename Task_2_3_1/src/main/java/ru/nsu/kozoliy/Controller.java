package ru.nsu.kozoliy;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label mainLabel;

    @FXML
    private ChoiceBox<String> worldSizeChoiceBox;

    private String[] worldSizes = {"Маленький", "Средний", "Большшой", "Огромный"};


    private Settings settings;

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        worldSizeChoiceBox.getItems().addAll(worldSizes);
        worldSizeChoiceBox.setOnAction(this::getWorldSize);
    }

    public void getWorldSize(ActionEvent event) {
        String worldSize = worldSizeChoiceBox.getValue();
        if (worldSize.equals("Маленький")) {
            System.out.println("400 x 500");
        } else if (worldSize.equals("Средний")) {
            System.out.println("600 x 800");
        } else if (worldSize.equals("Большшой>")) {
            System.out.println("800 x 1000");
        } else if (worldSize.equals("Огромный")) {
            System.out.println("1920 x 1080");
        }
    }
}
