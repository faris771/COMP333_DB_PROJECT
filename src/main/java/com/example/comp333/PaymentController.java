package com.example.comp333;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    Stage stage = new Stage ();
    Scene scene = null;

    @FXML
    TableView<Payment> paymentTableView;

    @FXML
    TableColumn<Payment, Integer> SSNCol;

    @FXML
    TableColumn<Payment, String> paymentTypeCol;

    @FXML
    TableColumn<Payment, Integer> paymentIDCol;

    @FXML
    TableColumn<Payment, String> paymentDateCol;

    @FXML
    TableColumn<Payment, Double> amountPaidCol;

    @FXML
    TextField ssnTextField;

    @FXML
    RadioButton cardRadioBtn;

    @FXML
    RadioButton cashRadioBtn;

    ToggleGroup paymentType = new ToggleGroup ();

    @FXML
    Label totalBillLabel;


    ObservableList<Payment> paymentObservableList = FXCollections.observableArrayList ();


    public  void  exitButton(ActionEvent event){
        HelloApplication.changeScene ( event, "MenuScene.fxml", "Login", 600 ,562 );
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cardRadioBtn.setToggleGroup ( paymentType );
        cashRadioBtn.setToggleGroup ( paymentType );

        DataBaseConnection connection = new DataBaseConnection ();

        String paymentsShowQuery = "SELECT * FROM Payment";

        try {

            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            ResultSet queryResult = statement.executeQuery ( paymentsShowQuery );

            while (queryResult.next ()) {
                paymentObservableList.add ( new Payment ( queryResult.getInt ( "Guest_SSN" ), queryResult.getString ( "Payment_way" ), queryResult.getString ( "Payment_Date" ), queryResult.getDouble ( "amountPaid" ), queryResult.getInt ( "Payment_id" ) ) );
            }

            SSNCol.setCellValueFactory ( new PropertyValueFactory<> ( "SSN" ) );
            paymentTypeCol.setCellValueFactory ( new PropertyValueFactory<> ( "paymentType" ) );
            paymentDateCol.setCellValueFactory ( new PropertyValueFactory<> ( "paymentDate" ) );
            amountPaidCol.setCellValueFactory ( new PropertyValueFactory<> ( "amountPaid" ) );
            paymentIDCol.setCellValueFactory ( new PropertyValueFactory<> ( "paymentID" ) );
            paymentTableView.setItems ( paymentObservableList );


        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }

    @FXML
    private void payBtnOnAction (ActionEvent event) {


        if (HelloApplication.isTextFieldEmpty ( ssnTextField )) {
            HelloApplication.AlertShow ( "Please enter SSN", "Error", Alert.AlertType.ERROR );
            return;
        }

        try {
            Integer.parseInt ( ssnTextField.getText () );
        } catch (NumberFormatException e) {
            HelloApplication.AlertShow ( "Please enter a valid SSN", "Error", Alert.AlertType.ERROR );
            return;
        }
        int SSN = Integer.parseInt ( ssnTextField.getText () );

        if (paymentType.getSelectedToggle () == null) {
            HelloApplication.AlertShow ( "Please select payment method", "Error", Alert.AlertType.ERROR );
            return;
        }

        DataBaseConnection connection = new DataBaseConnection ();

        String paymentType = null;

        if (cardRadioBtn.isSelected ()) {
            paymentType = "Card";
        } else if (cashRadioBtn.isSelected ()) {
            paymentType = "Cash";
        }

        double amountPaid = 0;

        try {

            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            String amountPaidQuery = "SELECT R.room_price FROM room R, booking B WHERE B.Guest_SSN = " + ssnTextField.getText () + " AND B.room_number = R.room_number";

            ResultSet bookingsResultSet = statement.executeQuery ( amountPaidQuery );

            while (bookingsResultSet.next ()) {
                amountPaid = bookingsResultSet.getDouble ( "room_price" );
            }

            if (amountPaid > 0) {
                String bookedRoomsUnBook = "UPDATE room SET room_status = 'FREE' WHERE room_number IN (SELECT room_number FROM booking WHERE Guest_SSN = " + SSN + ")";
                String bookedRoomsDelete = "DELETE FROM booking WHERE Guest_SSN = " + SSN;
                statement.executeUpdate ( bookedRoomsUnBook );
                statement.executeUpdate ( bookedRoomsDelete );
            }

            String amountPaidOnServicesQuery = "SELECT S.service_price FROM service S, service_to_room STR, booking B WHERE B.Guest_SSN = " + SSN + " AND B.room_number = STR.room_number AND STR.service_id = S.service_id";

            Statement statement1 = connectDB.createStatement ();
            ResultSet servicesResultSet = statement1.executeQuery ( amountPaidOnServicesQuery );


            double servicePayment = 0;
            while (servicesResultSet.next ()) {
                amountPaid += servicesResultSet.getDouble ( "service_price" );
                servicePayment += servicesResultSet.getDouble ( "service_price" );
            }

            if (servicePayment > 0) {

                statement.executeUpdate ( "UPDATE service_to_room SET isPaid = 'TRUE' WHERE room_number IN (SELECT room_number FROM booking WHERE Guest_SSN = " + SSN + ");" );

            }

            totalBillLabel.setText ( amountPaid + "" );

            statement.close ();

        } catch (SQLException e) {
            e.printStackTrace ();
        }

        String paymentInsertQuery = "INSERT INTO Payment (Guest_SSN, Payment_way, Payment_Date, amountPaid) VALUES ('" + SSN + "', '" + paymentType + "', '" + java.time.LocalDate.now () + "', '" + amountPaid + "'" + ")";

        try {
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            if (amountPaid > 0)
                statement.executeUpdate ( paymentInsertQuery );
            else
                HelloApplication.AlertShow ( "No bill to pay", "Error", Alert.AlertType.ERROR );


        } catch (SQLException e) {
            e.printStackTrace ();
        }

    }

}
