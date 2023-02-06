package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
    Stage stage = null;
    Scene scene = null;
    @FXML
    public  void  exitButton(ActionEvent event){
        HelloApplication.changeScene ( event, "LogInScene.fxml", "Login" , 410 , 500);
    }
    @FXML
     // this method to enter the guest scene
    public  void  GuestButton(ActionEvent event ){
        HelloApplication.changeScene ( event, "Guest.fxml", "Guest" , 760 , 600);
    }
    @FXML
    public  void  serviceButton (ActionEvent event ) {
        HelloApplication.changeScene ( event, "Services.fxml", "Service" , 760 , 600);
    }
    @FXML
    public  void  PaymentButton (ActionEvent event ) {
        HelloApplication.changeScene ( event, "PaymentScene.fxml", "Payment",760 , 600 );
    }
    @FXML
    public  void  BookingButton (ActionEvent event ) {
        HelloApplication.changeScene ( event, "Booking.fxml", "Booking",760 , 600 );
    }
    @FXML
    public  void  roomBtn (ActionEvent event ) {
        HelloApplication.changeScene ( event, "Phone_empScene.fxml", "Room",760 , 600 );
    }

    @FXML
    public  void  phoneBtn (ActionEvent event ) {
        HelloApplication.changeScene ( event, "Phone_empScene.fxml", "Phones of employees",760 , 600 );
    }
    public  void  ServiceToRoomBtn (ActionEvent event ) {
        HelloApplication.changeScene ( event, "ServiceToRoom.fxml", "Service To Room",720 , 600 );
    }

}

