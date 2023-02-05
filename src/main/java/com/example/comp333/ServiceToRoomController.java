package com.example.comp333;

import javafx.event.ActionEvent;

public class ServiceToRoomController {
    public void exitButton(ActionEvent event) {
        HelloApplication.changeScene(event, "MenuScene.fxml", "Login", 600, 562);
    }
}
