package com.example.comp333;
// hamza branch
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
// Hamza Awashra
import java.io.IOException;
// testing pull
public class HelloApplication extends Application {
    //test
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LogInScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600,500);
        stage.setTitle("Hotel DataBase!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e->{
            e.consume();
            logout(stage);
        });
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("log out");
        alert.setHeaderText("You are about to logout");


        alert.setContentText("Are you sure you want to quit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("successfully logged out");
            stage.close();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}
