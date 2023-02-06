package com.example.comp333;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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


public class ServiceController implements Initializable {
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

    // talbeview stuff
    @FXML
    private TableView<Service> tableView;
    @FXML
    private TableColumn<Service, Integer> serviceIDCol;
    @FXML
    private TableColumn<Service, String> serviceTypeCol;
    @FXML
    private TableColumn<Service, Integer> servicePriceCol;
    private ObservableList<Service> serviceObservableListList = FXCollections.observableArrayList();


    // buttons actions
    @FXML
    private void addBtnOnAction(ActionEvent event) throws SQLException {
        DataBaseConnection connectNow = new DataBaseConnection();

        Connection connection = connectNow.getConnection(); // connection to the database
        PreparedStatement preparedStatement = null; // to execute the query
        Service dummyService = null; // to store the data from the text fields

        boolean flag = false;
        try {

            String tstIfEmpty = (seviceIDField.getText());
            int serviceID;

            if (seviceIDField.getText().isBlank() || serviceTypeField.getText().isBlank() || servicePriceField.getText().isBlank()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please fill the required fields");
                alert.setContentText("");
                alert.showAndWait();
                return;
            }
            try {
                serviceID = Integer.parseInt(seviceIDField.getText());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Service ID must be an integer");
                alert.setContentText("");
                alert.showAndWait();
                return;
            }

            try {
                dummyService = new Service(serviceID, serviceTypeField.getText(),
                        Double.parseDouble(servicePriceField.getText()));

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a valid service ID and price");
                alert.showAndWait();
                return;
            }

            if (!checkIfServiceEXists(dummyService.getServiceID())) {


                preparedStatement = connection.prepareStatement("INSERT INTO  SERVICE VALUES (?,?,?)");

                preparedStatement.setString(1,  dummyService.getServiceID()+ "");
                preparedStatement.setString(2, dummyService.getServiceType());
                preparedStatement.setString(3, String.valueOf(dummyService.getServicePrice())); // makes the double value STRING
                preparedStatement.execute();
                HelloApplication.AlertShow("Service Added Successfully", "ADDED ", Alert.AlertType.INFORMATION);


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
        serviceObservableListList.clear(); // clear the list to avoid duplicates when adding new data to the table
        tableView.refresh(); // guess it's useless but keep it

        REFRESH();

    }

    @FXML
    private void deleteBtnOnAction(ActionEvent event) throws SQLException {

        if (seviceIDField.getText().isBlank()) { // if the text field is empty then return
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID IS EMPTY");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }
        int tobeDeletedServiceID = Integer.parseInt(seviceIDField.getText()); // get the ID from the text field


        if (checkIfServiceEXists(tobeDeletedServiceID)) { // if the service exists then delete it

            try {
                DataBaseConnection connectNow = new DataBaseConnection();
                Connection connection = connectNow.getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM SERVICE WHERE service_id  = ?");


                ps.setString(1, tobeDeletedServiceID + "");

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
            HelloApplication.AlertShow("Error, no such guest exists!", "Guest not found!!", Alert.AlertType.ERROR); // if the service does not exist then show an error message
        }
        serviceObservableListList.clear();

        tableView.refresh();
        REFRESH();

    }

    @FXML
    private void updateBtnOnAction(ActionEvent event) throws SQLException {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();
        PreparedStatement preparedStatement = null;
        if (serviceTypeField.getText().isBlank() || servicePriceField.getText().isBlank() || seviceIDField.getText().isBlank()) { // if the text field is empty then return
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill all the fields");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }



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
        serviceObservableListList.clear();
        tableView.refresh();
        REFRESH();

    }


    private boolean checkIfServiceEXists(int serviceID) throws SQLException { // check if the service exists in the database
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
        HelloApplication.changeScene(event, "MenuScene.fxml", "Login", 600, 562);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { // initialize the table view
        DataBaseConnection connection = new DataBaseConnection();
        String guestShowQuery = "SELECT * FROM Service";
        try {

            Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();
            ResultSet queryRes = statement.executeQuery(guestShowQuery);
            while (queryRes.next()) {
                int serviceID = (queryRes.getInt("service_id"));
                String serviceType = queryRes.getString("service_type");
                double servicePrice = (queryRes.getDouble("service_price"));
                serviceObservableListList.add(new Service(serviceID, serviceType, servicePrice));

            }
            serviceIDCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
            serviceTypeCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
            servicePriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
            tableView.setItems(serviceObservableListList);

        } catch (SQLException e) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void REFRESH() { // refresh the table view
        DataBaseConnection connection = new DataBaseConnection();
        String guestShowQuery = "SELECT * FROM Service";
        try {

            Connection connectDB = connection.getConnection();
            Statement statement = connectDB.createStatement();
            ResultSet queryRes = statement.executeQuery(guestShowQuery);
            while (queryRes.next()) {
                int serviceID = (queryRes.getInt("service_id"));
                String serviceType = queryRes.getString("service_type");
                double servicePrice = (queryRes.getDouble("service_price"));
                serviceObservableListList.add(new Service(serviceID, serviceType, servicePrice));

            }
            serviceIDCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
            serviceTypeCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
            servicePriceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
            tableView.setItems(serviceObservableListList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
