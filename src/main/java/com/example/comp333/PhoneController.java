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
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhoneController implements Initializable {
    Stage stage = new Stage();
    Scene scene = null;

    @FXML
    private TextField phoneField;
    @FXML
    private TextField employeeIDField;

    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;


    @FXML
    private TableView<Phone> phoneTableView;
    @FXML
    private TableColumn<Phone, String> phoneColumn;
    @FXML
    private TableColumn<Phone, Integer> employeeIDColumn;

    private ObservableList<Phone> phoneObservableListList = FXCollections.observableArrayList(); // create observable list of phones






    @Override
    public void initialize(URL url, ResourceBundle rb) { // initialize the table view
        DataBaseConnection connection = new DataBaseConnection();
        String guestShowQuery = "SELECT * FROM employee_phone";

        try {

            Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();
            ResultSet queryRes = statement.executeQuery(guestShowQuery);
            while (queryRes.next()) {

                String phoneNumber = queryRes.getString("PHONE_NUM");
                int employeeID = queryRes.getInt("EID");
                phoneObservableListList.add(new Phone(phoneNumber, employeeID));

            }
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
            phoneTableView.setItems(phoneObservableListList);

        } catch (SQLException e) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void REFRESH() {
        phoneObservableListList.clear();

        DataBaseConnection connection = new DataBaseConnection();
        String guestShowQuery = "SELECT * FROM employee_phone";

        try {

            Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();
            ResultSet queryRes = statement.executeQuery(guestShowQuery);
            while (queryRes.next()) {

                String phoneNumber = queryRes.getString("PHONE_NUM");
                int employeeID = queryRes.getInt("EID");
                phoneObservableListList.add(new Phone(phoneNumber, employeeID));

            }
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
            phoneTableView.setItems(phoneObservableListList);

        } catch (SQLException e) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfPhoneEmpExist(String phone, int employeeID) throws SQLException { // check if the service exists in the database


        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean empPhone = false;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM employee_phone WHERE phone_num = ? AND  eid = ? "); // check if the emp_phone exists in the database
            preparedStatement.setString(1, phone + "");
            preparedStatement.setString(2, employeeID + "");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // if there is a result
                empPhone = true;
            }

        } catch (SQLException ex) { // if there is an error
            ex.printStackTrace();
            ex.getCause();
        }
        /*finally { // if there is no error
            preparedStatement.close();
            resultSet.close();
        }*/

        return empPhone;
    }


    @FXML
    private void deleteBtnOnAction(ActionEvent event) throws SQLException {

        if (phoneField.getText().isBlank() || employeeIDField.getText().isBlank()) { // if the text field is empty then return

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("PLEASE FILL BOTH FIELDS");
            alert.setContentText("");
            alert.showAndWait();
            return; // return to the method and do not execute the rest of the code
        }


        String tobBeDeletedPhone = phoneField.getText();
        int toBeDeletedEmployeeID;
        try {
            toBeDeletedEmployeeID = Integer.parseInt(employeeIDField.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("PLEASE ENTER A VALID EMPLOYEE ID");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }

        if (checkIfPhoneEmpExist(tobBeDeletedPhone,toBeDeletedEmployeeID )) { // if the service exists then delete it

            try {
                DataBaseConnection connectNow = new DataBaseConnection();
                Connection connection = connectNow.getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM employee_phone WHERE phone_num  = ? AND eid = ? "); // delete the service from the database


                ps.setString(1, tobBeDeletedPhone + "");
                ps.setString(2, toBeDeletedEmployeeID + "");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete the service?");
                alert.setHeaderText("Please confirm your action");

                Optional<ButtonType> result = alert.showAndWait(); // wait for the user to click on the button

                if (result.isPresent() && result.get() == ButtonType.OK) { // if the user clicked on the OK button then delete the service
                    ps.execute();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            HelloApplication.AlertShow("Error, no such Employee or phone exist!", "not found!!", Alert.AlertType.ERROR); // if the service does not exist then show an error message
        }

        REFRESH();// refresh the table view after the deletion

    }

    @FXML
    public void addButtonOnAction(ActionEvent event) throws SQLException {

        if (phoneField.getText().isBlank() || employeeIDField.getText().isBlank()) { // if the text field is empty then return

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("PLEASE FILL BOTH FIELDS");
            alert.setContentText("");
            alert.showAndWait();
            return; // return to the method and do not execute the rest of the code
        }
        if(phoneField.getText().length() != 10){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("PLEASE ENTER A VALID PHONE NUMBER");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }
        try {
            int dummy = Integer.parseInt(phoneField.getText());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("PLEASE ENTER A VALID PHONE NUMBER");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }

        String phone = phoneField.getText();
        int employeeID;
        try {
            employeeID = Integer.parseInt(employeeIDField.getText());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("PLEASE ENTER A VALID EMPLOYEE ID");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }

        if (!checkIfPhoneEmpExist(phone, employeeID)) { // if the service does not exist then add it

            try {
                DataBaseConnection connectNow = new DataBaseConnection();
                Connection connection = connectNow.getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO employee_phone (phone_num, eid) VALUES (?,?)"); // add the service to the database

                ps.setString(1, phone + "");
                ps.setString(2, employeeID + "");

                ps.execute();
                HelloApplication.AlertShow("Added Successfully", "ADDED ", Alert.AlertType.INFORMATION);


            }catch (SQLIntegrityConstraintViolationException e){
                HelloApplication.AlertShow("Employee Doesn't exist", "Error", Alert.AlertType.ERROR);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            HelloApplication.AlertShow("Error, this Employee or phone already exist!", "not found!!", Alert.AlertType.ERROR); // if the service already exists then show an error message
        }

        REFRESH();// refresh the table view after the addition

    }



    public void exitButton(ActionEvent event) {
        HelloApplication.changeScene(event, "MenuScene.fxml", "Login", 600, 500);
    }

}

