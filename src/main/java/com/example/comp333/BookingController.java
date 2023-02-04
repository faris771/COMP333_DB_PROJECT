package com.example.comp333;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class BookingController {



    @FXML
    private void exitBtn(ActionEvent event) {
        HelloApplication.changeScene ( event, "MenuScene.fxml" , "Booking", 760, 600);
    }

    @FXML
    private DatePicker myDatePicker;
    @FXML
  //  private Label myLabel;

    public void getDate(ActionEvent event) {

        LocalDate myDate = myDatePicker.getValue();

    }
}

