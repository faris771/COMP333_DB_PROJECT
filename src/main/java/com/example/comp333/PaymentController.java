package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaymentController {
    Stage stage = new Stage ();
    Scene scene = null;
    public  void  exitButton(ActionEvent event){
        HelloApplication.changeScene ( event, "MenuScene.fxml", "Login", 600 ,500 );
    }
}