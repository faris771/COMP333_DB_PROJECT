package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Optional;


public class ServiceController {
    Stage stage = new Stage();
    Scene scene = null;

    @FXML
    private TextField seviceIDField; // service ID
    @FXML
    private TextField serviceTypeField;
    @FXML
    private TextField servicePriceField; // or cost
    @FXML
    private TextField serviceDateTimeField; // WILL BE DELETED

    // buttons
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;


    // buttons actions
    @FXML
    private void addBtnOnAction(ActionEvent event) throws SQLException {
        DataBaseConnection connectNow = new DataBaseConnection();

        Connection connection = connectNow.getConnection(); // COULD BE CHANGED WITH THE  DataBaseConnection class
        PreparedStatement preparedStatement = null;

        try {
            Service dummyService = new Service(Integer.parseInt(seviceIDField.getText()), serviceTypeField.getText(),
                    Double.parseDouble(servicePriceField.getText()));

            if (!checkIfServiceEXists(dummyService.getServiceID())) {

//                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_comp333", "root", "password");
                preparedStatement = connection.prepareStatement("INSERT INTO  SERVICE VALUES (?,?,?)");

                preparedStatement.setString(1, dummyService.getServiceID() + "");
                preparedStatement.setString(2, dummyService.getServiceType());
                preparedStatement.setString(3, String.valueOf(dummyService.getServicePrice())); // makes the double value STRING
                preparedStatement.execute();

            } else {
                HelloApplication.clearTextFields(seviceIDField);
                HelloApplication.AlertShow("Error, a service with this ID already exists", "duplicate service ID", Alert.AlertType.ERROR);

            }

//            HelloApplication.clearTextFields(SSN_field, firstNameField, familyNameField,
//                    fatherNameField, nationalityField, emailField);

        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    private void deleteBtnOnAction(ActionEvent event) throws SQLException {

        int tobeDeletedServiceID = Integer.parseInt(seviceIDField.getText());

        if (checkIfServiceEXists(tobeDeletedServiceID)) {

            try {
                DataBaseConnection connectNow = new DataBaseConnection();
                Connection connection = connectNow.getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM SERVICE WHERE service_id  = ?");


                ps.setString(1, tobeDeletedServiceID + "");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete the service?");
                alert.setHeaderText("Please confirm your action");

                Optional<ButtonType> result = alert.showAndWait(); // ??

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ps.execute();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            HelloApplication.AlertShow("Error, no such guest exists!", "Guest not found!!", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void updateBtnOnAction(ActionEvent event) throws SQLException {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            Service dummyService = new Service(Integer.parseInt(seviceIDField.getText()), serviceTypeField.getText(),
                    Double.parseDouble(servicePriceField.getText()));

            if (checkIfServiceEXists(dummyService.getServiceID())) {

                preparedStatement = connection.prepareStatement("UPDATE SERVICE SET service_type = ?, service_price = ? WHERE service_id = ?");

                preparedStatement.setString(1, dummyService.getServiceType());
                preparedStatement.setString(2, String.valueOf(dummyService.getServicePrice())); // makes the double value STRING
                preparedStatement.setString(3, dummyService.getServiceID() + "");
                preparedStatement.execute();

            } else {
                HelloApplication.clearTextFields(seviceIDField);
                HelloApplication.AlertShow("Error, no such service exists!", "Service not found!!", Alert.AlertType.ERROR);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }



    private boolean checkIfServiceEXists(int serviceID) throws SQLException {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean serviceExists = false;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM service WHERE Service_ID = ?");
            preparedStatement.setString(1, serviceID + "");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // if there is a result
                serviceExists = true;
            }

        } catch (SQLException ex) { // if there is an error
            ex.printStackTrace();
            ex.getCause();
        }
        /*finally { // if there is no error
            preparedStatement.close();
            resultSet.close();
        }*/

        return serviceExists;
    }


    public void exitButton(ActionEvent event) {
        HelloApplication.changeScene(event, "MenuScene.fxml", "Login", 600, 500);
    }


}
