package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The folder containing the Javadoc files is located in the zipped folder attached in the submission and named "Javadoc".
 * This is the main class
 * It creates the application for the inventory management system
 */
public class Main extends Application {

    /**
     * The first screen will be the main screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/MainScreen.fxml")));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This will launch the application
     * No sample data has been created
     */
    public static void main(String[] args) {
        launch();
    }
}