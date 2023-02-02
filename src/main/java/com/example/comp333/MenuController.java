package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
    Stage stage = null;
    Scene scene = null;
    public  void  exitButton(ActionEvent event){
        HelloApplication.changeScene ( event, "LogInScene.fxml", "Login" );
    }
     // this method to enter the guest scene
    public  void  GuestButton(ActionEvent event ){
        HelloApplication.changeScene ( event, "Guset.fxml", "Guest" );
    }

    public  void  serviceButton (ActionEvent event ) {
        HelloApplication.changeScene ( event, "Services.fxml", "Service" );
    }



}
