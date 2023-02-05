package com.example.comp333;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookingController implements Initializable {

    @FXML
    TextField SSNTextField;

    @FXML
    TextField roomNumberTextField;

    @FXML
    DatePicker startDatePicker;

    @FXML
    DatePicker endDatePicker;


    @FXML
    TableView<Booking> bookingTableView;

    @FXML
    Button delBtn;

    @FXML
    Button addBtn;

    @FXML
    Button exitBtn;

    @FXML
    Button updBtn;

    @FXML
    TableColumn<Booking, Integer> SSNCol;
    @FXML
    TableColumn<Booking, Integer> IDCol;

    @FXML
    TableColumn<Booking, String> startDateCol;

    @FXML
    TableColumn<Booking, String> endDateCol;


    ObservableList<Booking> bookingObservableList = FXCollections.observableArrayList ();

    @FXML
    // Delete booking from database
    private void exitBtn(ActionEvent event) {
        HelloApplication.changeScene ( event, "MenuScene.fxml" , "Booking", 600, 562);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseConnection connection = new DataBaseConnection ();
        String bookingShowQuery = "SELECT * FROM booking";

        try {
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            ResultSet resultSet = statement.executeQuery ( bookingShowQuery );

            while (resultSet.next ()) {

                String startDate = HelloApplication.formatter.format ( resultSet.getDate ( "STARTING_DATE" ) );
                String endDate = HelloApplication.formatter.format ( resultSet.getDate ( "end_date" ) );
                bookingObservableList.add ( new Booking ( resultSet.getInt ( "guest_ssn" ), resultSet.getInt ( "Booking_id" ), resultSet.getString ( "room_number" )
                        , startDate, endDate));
            }
        } catch (Exception e) {
            e.printStackTrace ();
            e.getCause ();
        }

        // Set cell value factory to tableview.
        SSNCol.setCellValueFactory ( new PropertyValueFactory<> ( "SSN" ) );
        IDCol.setCellValueFactory ( new PropertyValueFactory<> ( "ID" ) );

        startDateCol.setCellValueFactory ( new PropertyValueFactory<> ( "startDate" ) );
        endDateCol.setCellValueFactory ( new PropertyValueFactory<> ( "endDate" ) );

        // Add observable list data to the table
        bookingTableView.setItems ( bookingObservableList );
    }

    @FXML
    // Add booking to database
    private void addBooking(ActionEvent event) {
        DataBaseConnection connection = new DataBaseConnection ();
        String bookingAddQuery = "INSERT INTO booking (guest_ssn, room_number, starting_date, end_date) VALUES ('" + SSNTextField.getText () + "', '" + roomNumberTextField.getText () + "', '" + startDatePicker.getValue ()  + "', '" +  endDatePicker.getValue ()  + "')";
        try {
            // Check if guest and room exists
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            Statement guestStatement = connectDB.createStatement ();
            guestStatement.executeQuery ( "SELECT * FROM guest WHERE Guest_SSN = " + SSNTextField.getText () );
            Statement roomStatement = connectDB.createStatement ();
            roomStatement.executeQuery ( "SELECT * FROM room WHERE Room_Number = " + roomNumberTextField.getText () );

            // Check if start date is before end date and if guest and room exists
            if (guestStatement.getResultSet ().next () && roomStatement.getResultSet ().next () && startDatePicker.getValue ().isBefore ( endDatePicker.getValue () )) {

                // Check if room is free to book it, or say that it is not free
                bookIfNotBooked ( bookingAddQuery, connectDB, statement, roomStatement );


            } else { // If guest or room does not exist, or start date is after end date
                System.out.println ( "Guest or room does not exist, or start date is after end date" );
                HelloApplication.AlertShow ( "Error, either Guest or room does not exist, or start date is after end date ", "Error, something went wrong.." , Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace ();
            e.getCause ();
        }
    }

    private void bookIfNotBooked(String bookingAddQuery, Connection connectDB, Statement statement, Statement roomStatement) throws SQLException {
        if (!Objects.equals ( roomStatement.getResultSet ().getString ( "room_status" ), "FREE" )) {
            HelloApplication.AlertShow ( "Room is not free, please choose another room", "Error, something went wrong.." , Alert.AlertType.ERROR);
        }

        statement.executeUpdate ( bookingAddQuery );
        bookingTableView.refresh();
        Statement roomSt = connectDB.createStatement ();
        roomSt.executeQuery ("UPDATE room set room.room_status = 'RESERVED' WHERE room_number = '" + roomNumberTextField.getText () + "'");

    }

    @FXML
    private void deleteBooking(ActionEvent event) {
        DataBaseConnection connection = new DataBaseConnection ();
        String bookingDeleteQuery = "DELETE FROM booking WHERE Booking_id = " + bookingTableView.getSelectionModel ().getSelectedItem ().getID ();
        try {
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
            alert.setContentText ( "Are you sure you want to delete the booking?" );
            alert.setHeaderText ( "Please confirm your action" );

            Optional<ButtonType> result = alert.showAndWait();

            if ( result.isPresent () && result.get() == ButtonType.OK) {
                statement.executeUpdate ( bookingDeleteQuery );
                bookingTableView.refresh();

            }


        } catch (Exception e) {
            e.printStackTrace ();
            e.getCause ();
        }
    }

    @FXML
    private void updateBooking(ActionEvent event) {
        DataBaseConnection connection = new DataBaseConnection ();
        String bookingUpdateQuery = "UPDATE booking SET guest_ssn = '" + SSNTextField.getText () + "', room_number = '" + roomNumberTextField.getText () + "', starting_date = '" + startDatePicker.getValue () + "', end_date = '" + endDatePicker.getValue () + "' WHERE Booking_id = " + bookingTableView.getSelectionModel ().getSelectedItem ().getID ();
        try {
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            Statement ssnVerifyStatement = connectDB.createStatement ();
            ssnVerifyStatement.executeQuery ( "SELECT * FROM booking WHERE Booking_id = " + SSNTextField.getText () );

            Statement roomVerifyStatement = connectDB.createStatement ();
            roomVerifyStatement.executeQuery ( "SELECT * FROM booking WHERE Booking_id = " + roomNumberTextField.getText () );

            // check if guest and room exists and if start date is before end date
            if (ssnVerifyStatement.getResultSet ().next () && roomVerifyStatement.getResultSet ().next () && startDatePicker.getValue ().isBefore ( endDatePicker.getValue () ) ) {
                bookIfNotBooked ( bookingUpdateQuery, connectDB, statement, roomVerifyStatement ); // check if room is free to book it, or say that it is not free

            } else { // if guest or room does not exist or start date is after end date
                System.out.println ( "Guest or room does not exist" );
                HelloApplication.AlertShow ( "Guest or room does not exist!!", "Error, something went wrong.." , Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace ();
            e.getCause ();
        }
    }

}

