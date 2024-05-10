package ru.nsu.kozoliy;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {


    @Override
    @ExcludeFromJacocoGeneratedReport
    public void start(Stage primaryStage) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setResizable(false);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);

            primaryStage.show();
            primaryStage.setTitle("Snake by Mello");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        launch(args);
    }
}