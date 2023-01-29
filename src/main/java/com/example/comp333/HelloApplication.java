

package com.example.comp333;
// hamza branch
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
// Hamza Awashra
import java.io.IOException;
// testing pull
public class HelloApplication extends Application {
    //test
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LogInScene.fxml"));
        // for the login scene put the size  410 , 500
        // for menu Scene put the size 500 , 600
        Scene scene = new Scene(fxmlLoader.load(), 600,500);
        stage.setTitle("Hotel DataBase!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

