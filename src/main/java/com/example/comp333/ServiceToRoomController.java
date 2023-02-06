package com.example.comp333;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ServiceToRoomController implements Initializable {

    @FXML
    private TextField serviceIDField;
    @FXML
    private TextField roomNumberField;
    @FXML
    private TextField employeeIDField;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;

    @FXML
    private TableView<ServiceToRoom> serviceToRoomTable;
    @FXML
    private TableColumn<ServiceToRoom, Integer> serviceIDColumn;
    @FXML
    private TableColumn<ServiceToRoom, Integer> roomNumberColumn;
    @FXML
    private TableColumn<ServiceToRoom, Integer> employeeIDColumn;
    @FXML
    private TableColumn<ServiceToRoom, String> serveDateColumn;
    @FXML
    private TableColumn<ServiceToRoom, String> serveTimeColumn;
    @FXML
    private TableColumn<ServiceToRoom, String> serviceIsPaidColumn;


    @FXML
    private TextField searchTextField;
    private ObservableList<ServiceToRoom> serviceToRoomObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseConnection connection = new DataBaseConnection();
        String guestShowQuery = "SELECT * FROM service_to_room";
        try {
            Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(guestShowQuery);

            while (queryResult.next()) {
                int serviceID = queryResult.getInt("service_id");
                int roomNumber = queryResult.getInt("room_number");
                int employeeID = queryResult.getInt("eid");
                boolean serviceIsPaidBool = queryResult.getBoolean("ispaid");

                String serviceIsPaid = String.valueOf(serviceIsPaidBool);

                //String dateTime = queryResult.getString("service_date_time"); // DEFAULT DATE ADDED TO TABLE VIEW
                //
                java.sql.Date date = queryResult.getDate("service_date");
//                String dateString = String.valueOf(queryResult.getDate("service_date")); // DEFAULT DATE ADDED TO TABLE VIEW
                String serviceDate = HelloApplication.formatter.format(date);

//                String timeString = String.valueOf(queryResult.getTime("service_time")); // DEFAULT DATE ADDED TO TABLE VIEW
                serviceToRoomObservableList.add(new ServiceToRoom(serviceID, roomNumber, employeeID, serviceDate, false)); // DEFAULT DATE ADDED TO TABLE VIEW

            }
            serviceIDColumn.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
            roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
            employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
            serveDateColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDate")); //SAME AS THE CLASS
            serviceIsPaidColumn.setCellValueFactory(new PropertyValueFactory<>("serviceIsPaid"));

//            serveTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeString")); // DEFAULT DATE ADDED TO TABLE VIEW

            serviceToRoomTable.setItems(serviceToRoomObservableList);
            FilteredList<ServiceToRoom> filteredData = new FilteredList<>(serviceToRoomObservableList, b -> true);
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(serviceToRoom -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (String.valueOf(serviceToRoom.getServiceID()).contains(newValue)) {
                        return true;
                    } else if (String.valueOf(serviceToRoom.getRoomNumber()).contains(newValue)) {
                        return true;
                    } else if (String.valueOf(serviceToRoom.getEmployeeID()).contains(newValue)) {
                        return true;
                    } else if (String.valueOf(serviceToRoom.getServiceDate()).contains(newValue)) {
                        return true;
                    } else if (String.valueOf(serviceToRoom.getServiceIsPaid()).contains(newValue)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ServiceToRoom> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(serviceToRoomTable.comparatorProperty());
            serviceToRoomTable.setItems(sortedData);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public  void REFRESH(){
        serviceToRoomObservableList.clear();
        DataBaseConnection connection = new DataBaseConnection();
        String guestShowQuery = "SELECT * FROM service_to_room";
        try {
            Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(guestShowQuery);

            while (queryResult.next()) {
                int serviceID = queryResult.getInt("service_id");
                int roomNumber = queryResult.getInt("room_number");
                int employeeID = queryResult.getInt("eid");
                boolean serviceIsPaidBool = queryResult.getBoolean("ispaid");
                String serviceIsPaid = String.valueOf(serviceIsPaidBool);


                //String dateTime = queryResult.getString("service_date_time"); // DEFAULT DATE ADDED TO TABLE VIEW
                //
                java.sql.Date date = queryResult.getDate("service_date");
//                String dateString = String.valueOf(queryResult.getDate("service_date")); // DEFAULT DATE ADDED TO TABLE VIEW
                String serviceDate = HelloApplication.formatter.format(date);

//                String timeString = String.valueOf(queryResult.getTime("service_time")); // DEFAULT DATE ADDED TO TABLE VIEW
                serviceToRoomObservableList.add(new ServiceToRoom(serviceID, roomNumber, employeeID, serviceDate,serviceIsPaidBool)); // DEFAULT DATE ADDED TO TABLE VIEW

            }
            serviceIDColumn.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
            roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
            employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
            serveDateColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDate")); //SAME AS THE CLASS
//            serveTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeString")); // DEFAULT DATE ADDED TO TABLE VIEW

            serviceToRoomTable.setItems(serviceToRoomObservableList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean checkIfServiceToRoomExist(int serviceID, int roomNumber) throws SQLException { // check if the service exists in the database


        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean serviceExists = false;


        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM service_to_room WHERE Service_ID = ? AND ROOM_NUMBER = ?");

            preparedStatement.setString(1, serviceID + "");
            preparedStatement.setString(2, roomNumber + "");

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // if there is a result
                serviceExists = true;
            }

        } catch (SQLException ex) { // if there is an error
            HelloApplication.AlertShow("Invalid Input", "Error", Alert.AlertType.ERROR);
        }
        /*finally { // if there is no error
            preparedStatement.close();
            resultSet.close();
        }*/

        return serviceExists;
    }

    public void addButtonOnAction(ActionEvent actionEvent) throws SQLException {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        String insertQuery = "INSERT INTO service_to_room (service_id, room_number, eid) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);

        if (serviceIDField.getText().isBlank() || roomNumberField.getText().isBlank() || employeeIDField.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("PLEASE FILL ALL FIELDS");
            alert.setContentText("");
            alert.showAndWait();
            return; // return to the method and do not execute the rest of the code
        }
        int serviceID;
        int roomNumber;
        int employeeID;
        try { // if the user enters a string instead of an integer
            serviceID = Integer.parseInt(serviceIDField.getText());
            roomNumber = Integer.parseInt(roomNumberField.getText());
            employeeID = Integer.parseInt(employeeIDField.getText());
        } catch (NumberFormatException e) {
            HelloApplication.AlertShow("Invalid Inputs", "ERROR", Alert.AlertType.ERROR);
            return;
        }
        if (checkIfServiceToRoomExist(serviceID, roomNumber)) {
            HelloApplication.AlertShow("Service already exists to this room", "Error", Alert.AlertType.ERROR);

        } else {

            preparedStatement.setInt(1, serviceID);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.setInt(3, employeeID);

            try {
                preparedStatement.execute();
            } catch (Exception e) {
                HelloApplication.AlertShow("make sure service, room, employee already exist", "Error", Alert.AlertType.ERROR);

                return;
            }
            HelloApplication.AlertShow( "Service added successfully","Success", Alert.AlertType.INFORMATION);
            REFRESH();

        }


    }

    public void delteButtonOnAction(ActionEvent actionEvent) throws SQLException {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        String deleteQuery = "DELETE FROM service_to_room WHERE service_id = ? AND room_number = ?";
        PreparedStatement preparedStatement = connectDB.prepareStatement(deleteQuery);

        if (serviceIDField.getText().isBlank() || roomNumberField.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("PLEASE FILL ALL FIELDS");
            alert.setContentText("");
            alert.showAndWait();
            return; // return to the method and do not execute the rest of the code
        }
        int serviceID;
        int roomNumber;
        try { // if the user enters a string instead of an integer
            serviceID = Integer.parseInt(serviceIDField.getText());
            roomNumber = Integer.parseInt(roomNumberField.getText());
        } catch (NumberFormatException e) {
            HelloApplication.AlertShow("Invalid Inputs", "ERROR", Alert.AlertType.ERROR);
            return;
        }
        if (!checkIfServiceToRoomExist(serviceID, roomNumber)) {
            HelloApplication.AlertShow("Service does not exist to this room", "Error", Alert.AlertType.ERROR);

        } else {

            preparedStatement.setInt(1, serviceID);
            preparedStatement.setInt(2, roomNumber);

            try {
                preparedStatement.execute();
            } catch (Exception e) {
                HelloApplication.AlertShow("make sure service, and room already exist","Error",  Alert.AlertType.ERROR);
                return;
            }
            HelloApplication.AlertShow( "Service deleted successfully","Success", Alert.AlertType.INFORMATION);
            REFRESH();

        }
    }

    public void updateButtonOnAction(ActionEvent event) {

    }


    public void exitButton(ActionEvent event) {
        HelloApplication.changeScene(event, "MenuScene.fxml", "Login", 600, 562);
    }
}