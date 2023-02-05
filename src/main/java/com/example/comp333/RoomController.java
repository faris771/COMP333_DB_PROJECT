package com.example.comp333;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomController implements Initializable {
    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private TableColumn<Room, Integer> roomNumCol;

    @FXML
    private TableColumn<Room, Double> roomPriceCol;

    @FXML
    private TableColumn<Room, Integer> roomNumOfBedsCol;

    @FXML
    private TableColumn<Room, String> roomTypeCol;

    @FXML
    private TableColumn<Room, String> roomStatusCol;

    @FXML
    private TextField searchTextField;

    ObservableList<Room> roomObservableList = FXCollections.observableArrayList ();


    public void exitButton(ActionEvent event) {
        HelloApplication.changeScene ( event, "MenuScene.fxml", "Login", 600, 562 );
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connection = new DataBaseConnection ();
        String guestShowQuery = "SELECT * FROM Room";

        try {

            Connection connectDB = connection.getConnection ();

            Statement statement = connectDB.createStatement ();
            ResultSet queryRes = statement.executeQuery ( guestShowQuery );

            while (queryRes.next ()) {
                int roomNum = queryRes.getInt ( "room_number" );
                double roomPrice = queryRes.getDouble ( "room_price" );
                int roomNumOfBeds = queryRes.getInt ( "number_of_beds" );
                String roomType = queryRes.getString ( "room_type" );
                String roomStatus = queryRes.getString ( "room_status" );
                roomObservableList.add ( new Room ( roomNum, roomPrice, roomNumOfBeds, roomType, roomStatus ) );

            }   // end of while loop

            roomNumCol.setCellValueFactory ( new PropertyValueFactory<> ("roomNumber") );
            roomPriceCol.setCellValueFactory ( new PropertyValueFactory<> ("roomPrice") );
            roomNumOfBedsCol.setCellValueFactory ( new PropertyValueFactory<> ("roomNumberOfBeds") );
            roomTypeCol.setCellValueFactory ( new PropertyValueFactory<> ("roomType") );
            roomStatusCol.setCellValueFactory ( new PropertyValueFactory<> ("roomStatus") );

            roomTableView.setItems ( roomObservableList );

            FilteredList<Room> filteredData = new FilteredList<> ( roomObservableList, b -> true );
            searchTextField.textProperty ().addListener ( (observable, oldValue, newValue) -> {
                filteredData.setPredicate ( room -> {
                    if (newValue == null || newValue.isEmpty ()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase ();

                    if ((room.getRoomNumber ()+"").toLowerCase ().contains ( lowerCaseFilter )) {
                        return true;
                    } else if ((room.getRoomPrice ()+"").toLowerCase ().contains ( lowerCaseFilter )) {
                        return true;
                    } else if ((room.getRoomNumberOfBeds ()+"").toLowerCase ().contains ( lowerCaseFilter )) {
                        return true;
                    } else if (room.getRoomType ().toLowerCase ().contains ( lowerCaseFilter )) {
                        return true;
                    } else return room.getRoomStatus ().toLowerCase ().contains ( lowerCaseFilter );
                } );
            } );

            SortedList<Room> sortedData = new SortedList<> ( filteredData );
            sortedData.comparatorProperty ().bind ( roomTableView.comparatorProperty () );
            roomTableView.setItems ( sortedData );

        } catch (SQLException e) {
            Logger.getLogger (GuestController.class.getName ()).log( Level.SEVERE, null, e );
            e.printStackTrace ();
        }

    }
}
