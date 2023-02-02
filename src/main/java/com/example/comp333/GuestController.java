package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.channels.AcceptPendingException;

public class GuestController {
    // here the exit button action
    private  Stage stage ;
    private  Scene scene;
    public  void  exitGuestScene(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MenuScene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // for menu Scene put the size 500 , 600
            scene = new Scene(fxmlLoader.load(), 600, 500);
            stage.setTitle("Hotel DataBase!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

