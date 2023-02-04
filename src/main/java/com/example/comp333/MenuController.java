package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
    Stage stage = null;
    Scene scene = null;
    public  void  exitButton(ActionEvent event){
        HelloApplication.changeScene ( event, "LogInScene.fxml", "Login" , 410 , 500);
    }
     // this method to enter the guest scene
    public  void  GuestButton(ActionEvent event ){
        HelloApplication.changeScene ( event, "Guset.fxml", "Guest" , 760 , 600);
    }

    public  void  serviceButton (ActionEvent event ) {
        HelloApplication.changeScene ( event, "Services.fxml", "Service" , 760 , 600);
    }
    public  void  PaymentButton (ActionEvent event ) {
        HelloApplication.changeScene ( event, "PaymentScene.fxml", "Payment",760 , 600 );
    }
    public  void  BookingButton (ActionEvent event ) {
        HelloApplication.changeScene ( event, "Booking.fxml", "Booking",760 , 600 );
    }
    public  void  roomBtn (ActionEvent event ) {
        HelloApplication.changeScene ( event, "RoomScene.fxml", "Room",760 , 600 );
    }

    public  void  phoneBtn (ActionEvent event ) {
        HelloApplication.changeScene ( event, "RoomScene.fxml", "Room",760 , 600 );
    }

}

