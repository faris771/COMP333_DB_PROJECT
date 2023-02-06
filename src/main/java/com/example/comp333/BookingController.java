package com.example.comp333;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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

import static com.example.comp333.HelloApplication.clearTextFields;
import static com.example.comp333.HelloApplication.isTextFieldEmpty;

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
    TableColumn<Booking, String> roomNumCol;

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
        roomNumCol.setCellValueFactory ( new PropertyValueFactory<> ( "roomNumber" ) );
        startDateCol.setCellValueFactory ( new PropertyValueFactory<> ( "startDate" ) );
        endDateCol.setCellValueFactory ( new PropertyValueFactory<> ( "endDate" ) );

        // Add observable list data to the table
        bookingTableView.setItems ( bookingObservableList );
    }
    @FXML
    // Add booking to database
    private void addBooking(ActionEvent event) {
        DataBaseConnection connection = new DataBaseConnection ();

        if (isTextFieldEmpty ( SSNTextField ) || isTextFieldEmpty ( roomNumberTextField ) || startDatePicker.getValue () == null || endDatePicker.getValue () == null) {
            HelloApplication.AlertShow ( "Please fill all the fields", "Error, something went wrong.." , Alert.AlertType.ERROR);
            return;
        }

        int SSN = Integer.parseInt ( SSNTextField.getText () );
        int roomNumber = Integer.parseInt ( roomNumberTextField.getText () );
        LocalDate startDate = startDatePicker.getValue ();
        LocalDate endDate = endDatePicker.getValue ();

        try {
            Integer.parseInt ( SSNTextField.getText () );

        } catch (NumberFormatException e) {
            HelloApplication.AlertShow ( "Please enter a valid SSN", "Error, something went wrong.." , Alert.AlertType.ERROR);
            return;
        }

        if (!startDate.isBefore ( endDate )) {
            HelloApplication.AlertShow ( "Start date must be before end date", "Error, something went wrong.." , Alert.AlertType.ERROR);
            return;
        }

        String bookingAddQuery = "INSERT INTO booking (guest_ssn, room_number, starting_date, end_date) VALUES ('" + SSN + "', '" + roomNumber + "', '" + startDate  + "', '" +  endDate  + "')";
        try {
            // Check if guest and room exists
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            Statement guestStatement = connectDB.createStatement ();
            guestStatement.executeQuery ( "SELECT * FROM guest WHERE Guest_SSN = " + SSN );
            Statement roomStatement = connectDB.createStatement ();
            roomStatement.executeQuery ( "SELECT * FROM room WHERE Room_Number = " + roomNumber );

            // Check if start date is before end date and if guest and room exists
            if (guestStatement.getResultSet ().next () && roomStatement.getResultSet ().next ()  && startDatePicker.getValue ().isBefore ( endDatePicker.getValue () ) ) {

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
        finally {
            HelloApplication.clearTextFields ( SSNTextField, roomNumberTextField);
            startDatePicker.setValue ( null );
            endDatePicker.setValue ( null );
        }
    }

    private void bookIfNotBooked(String bookingAddQuery, Connection connectDB, Statement statement, Statement roomStatement) throws SQLException {
        if (!Objects.equals ( roomStatement.getResultSet ().getString ( "room_status" ), "FREE" )) {
            HelloApplication.AlertShow ( "Room is not free, please choose another room", "Error, something went wrong.." , Alert.AlertType.ERROR);
            return;
        }


        statement.executeUpdate ( bookingAddQuery );
        bookingTableView.refresh();

        // update old room status to free (if called from update)
        Statement oldRoomSt = connectDB.createStatement ();
        if (bookingTableView.getSelectionModel ().getSelectedItem () != null)
            oldRoomSt.execute ("UPDATE room set room.room_status = 'FREE' WHERE room_number = '" + bookingTableView.getSelectionModel ().getSelectedItem ().getRoomNumber () + "'");

        // Update room status to reserved
        Statement roomSt = connectDB.createStatement ();

        roomSt.execute ("UPDATE room set room.room_status = 'RESERVED' WHERE room_number = '" + roomNumberTextField.getText () + "'");
        roomSt.close ();

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

                // Update room status to free
                Statement roomSt = connectDB.createStatement ();
                roomSt.execute ("UPDATE room set room.room_status = 'FREE' WHERE room_number = '" + bookingTableView.getSelectionModel ().getSelectedItem ().getRoomNumber () + "'");
                roomSt.close ();
                statement.executeUpdate ( bookingDeleteQuery );

                refreshTable();
            }


        } catch (Exception e) {
            e.printStackTrace ();
            e.getCause ();
        }
        finally {
            HelloApplication.clearTextFields ( SSNTextField, roomNumberTextField);
            startDatePicker.setValue ( null );
            endDatePicker.setValue ( null );
            refreshTable ();
        }
    }

    @FXML
    private void updateBooking(ActionEvent event) {
        DataBaseConnection connection = new DataBaseConnection ();

        if (bookingTableView.getSelectionModel ().getSelectedItem () == null) {
            HelloApplication.AlertShow ( "Please select a booking to update", "Error, something went wrong.." , Alert.AlertType.ERROR);
            return;
        }

        try {
            if (!isTextFieldEmpty ( SSNTextField )) {
                Integer.parseInt ( SSNTextField.getText () );
            }
        } catch (Exception e) {
            HelloApplication.AlertShow ( "Please enter a valid SSN or leave it empty for no update ", "Error, something went wrong.." , Alert.AlertType.ERROR);
            return;
        }

        int SSN = isTextFieldEmpty(SSNTextField) ? bookingTableView.getSelectionModel ().getSelectedItem ().getSSN () : Integer.parseInt ( SSNTextField.getText () );
        int roomNumber = isTextFieldEmpty(roomNumberTextField) ? Integer.parseInt ( bookingTableView.getSelectionModel ().getSelectedItem ().getRoomNumber () ) : Integer.parseInt ( roomNumberTextField.getText () );
        LocalDate startDate = (startDatePicker.getValue () == null) ? LocalDate.parse ( bookingTableView.getSelectionModel ().getSelectedItem ().getStartDate () ) : startDatePicker.getValue ();
        LocalDate endDate = endDatePicker.getValue () == null ? LocalDate.parse ( bookingTableView.getSelectionModel ().getSelectedItem ().getEndDate () ) : endDatePicker.getValue ();

        String bookingUpdateQuery = "UPDATE booking SET guest_ssn = '" + SSN + "', room_number = '" + roomNumber + "', starting_date = '" + startDate  + "', end_date = '" + endDate + "' WHERE Booking_id = " + bookingTableView.getSelectionModel ().getSelectedItem ().getID ();
        try {
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            Statement ssnVerifyStatement = connectDB.createStatement ();
            ssnVerifyStatement.executeQuery ( "SELECT * FROM guest WHERE Guest_SSN = '" + SSN +"'" );

            Statement roomVerifyStatement = connectDB.createStatement ();
            roomVerifyStatement.executeQuery ( "SELECT * FROM room WHERE room_number = " + roomNumber );


            // check if guest and room exists and if start date is before end date
            if (ssnVerifyStatement.getResultSet ().next () && roomVerifyStatement.getResultSet ().next () && startDate.isBefore ( endDate ) ) {
                bookIfNotBooked ( bookingUpdateQuery, connectDB, statement, roomVerifyStatement ); // check if room is free to book it, or say that it is not free

            }

            else if (!ssnVerifyStatement.getResultSet ().next ()) {
                System.out.println ( "Guest does not exist" );
                System.out.println ();
                HelloApplication.AlertShow ( "Guest does not exist!!", "Error, something went wrong.." , Alert.AlertType.ERROR);
            } else if (!roomVerifyStatement.getResultSet ().next ()){ // if guest or room does not exist or start date is after end date
                System.out.println ( "Room does not exist" );
                HelloApplication.AlertShow ( "Guest or room does not exist!!", "Error, something went wrong.." , Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace ();
            e.getCause ();
        }
        finally {
            HelloApplication.clearTextFields ( SSNTextField, roomNumberTextField);
            startDatePicker.setValue ( null );
            endDatePicker.setValue ( null );
            refreshTable ();
        }
    }

    // method to refresh table data to show the changes immediately
    private void refreshTable() {
        DataBaseConnection connection = new DataBaseConnection ();
        String bookingQuery = "SELECT * FROM booking";
        try {
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();
            statement.executeQuery ( bookingQuery );

            bookingTableView.getItems ().clear ();
            while (statement.getResultSet ().next ()) {
                bookingTableView.getItems ().add ( new Booking ( Integer.parseInt ( statement.getResultSet ().getString ( "Booking_id" ) ), Integer.parseInt ( statement.getResultSet ().getString ( "guest_ssn" ) ),
                        statement.getResultSet ().getString ( "room_number" ), HelloApplication.formatter.format( statement.getResultSet ().getDate ( "starting_date" )), HelloApplication.formatter.format( statement.getResultSet ().getDate ( "end_date" ))));
            }

            statement.close ();

        } catch (Exception e) {
            e.printStackTrace ();
            e.getCause ();
        }
    }


}

