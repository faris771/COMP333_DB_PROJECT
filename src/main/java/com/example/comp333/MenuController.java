package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.example.comp333.HelloApplication.changeScene;

public class MenuController {
    Stage stage = null;
    Scene scene = null;
    @FXML
    public  void  exitButton(ActionEvent event){
        changeScene ( event, "LogInScene.fxml", "Login" , 410 , 500);
    }
    @FXML
     // this method to enter the guest scene
    public  void  GuestButton(ActionEvent event ){
        changeScene ( event, "Guest.fxml", "Guest" , 1200 , 600);
    }
    @FXML
    public  void  serviceButton (ActionEvent event ) {
        changeScene ( event, "Services.fxml", "Service" , 760 , 600);
    }
    @FXML
    public  void  PaymentButton (ActionEvent event ) {
        changeScene ( event, "PaymentScene.fxml", "Payment",760 , 600 );
    }
    @FXML
    public  void  BookingButton (ActionEvent event ) {
        changeScene ( event, "Booking.fxml", "Booking",1000 , 604 );
    }
    @FXML
    public  void  roomBtn (ActionEvent event ) {
        changeScene ( event, "RoomScene.fxml", "Room",760 , 600 );
    }

    @FXML
    public  void  phoneBtn (ActionEvent event ) {
        changeScene ( event, "Phone_empScene.fxml", "Phones of employees",760 , 600 );
    }
    public  void  ServiceToRoomBtn (ActionEvent event ) {
        changeScene ( event, "ServiceToRoom.fxml", "Service To Room",720 , 600 );
    }

}

