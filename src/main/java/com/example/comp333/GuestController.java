package com.example.comp333;
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


public class GuestController implements Initializable {
    // here the exit button action
    private  Stage stage ;
    private  Scene scene;

    @FXML
    private TextField SSN_field;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField fatherNameField;
    @FXML
    private TextField familyNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nationalityField;

    // tableView for guest data
    @FXML
    private TableView<Guest> tableView;

    @FXML
    TextField searchTextField;

    @FXML
    TextField phoneNumberTextField;

    @FXML
    TableColumn<Guest, Integer> SSNCol;
    @FXML
    TableColumn<Guest, String> firstNameCol;
    @FXML
    TableColumn<Guest, String> fatherNameCol;
    @FXML
    TableColumn<Guest, String> familyNameCol;

    @FXML
    TableColumn<Guest, String> emailCol;

    @FXML
    TableColumn<Guest, String> nationalityCol;

    // observable List
    ObservableList<Guest> guestObservableList = FXCollections.observableArrayList ();

    public  void  exitGuestScene(ActionEvent event) {
        HelloApplication.changeScene ( event,  "MenuScene.fxml","Hotel DataBase!" ,600, 562);
    }

    @FXML
    private void fillFieldsWithSearchRes (ActionEvent event) {
        PreparedStatement preparedStatement = null;
        try {

            int SSN = Integer.parseInt ( SSN_field.getText () );
            // guest exists in the database
            if (checkIfGuestExists ( SSN )) {
                DataBaseConnection connectDB = new DataBaseConnection ();
                Connection connection = connectDB.getConnection ();

                // get guest data
                preparedStatement = connection.prepareStatement ( "SELECT * FROM guest G WHERE G.Guest_SSN = ?" );
                preparedStatement.setString ( 1, SSN + "" );

                // exec the query to get guest data for updating
                ResultSet rs1 = preparedStatement.executeQuery ();

                // here we have old guest information in text fields, we can overwrite them to update.
               while (rs1.next ()) {
                   SSN_field.setText ( rs1.getInt ( "Guest_SSN" ) + "" );
                   firstNameField.setText ( rs1.getString ( "Guest_first_Name" ) );
                   fatherNameField.setText ( rs1.getString ( "Guest_father_Name" ) );
                   familyNameField.setText ( rs1.getString ( "Guest_family_Name" ) );
                   emailField.setText ( rs1.getString ( "Guest_email" ) );
                   nationalityField.setText ( rs1.getString ( "Guest_nationality" ) );
               }
            }
        } catch (SQLException e) {
            e.printStackTrace ();
            e.getCause ();
        }

    }

    @FXML
    private void addBtnOnAction (ActionEvent event) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            try {
                Integer.parseInt ( SSN_field.getText () );
            }catch (Exception e) {
                HelloApplication.AlertShow ( "Please enter a valid SSN, only digits are allowed", "Invalid SSN", Alert.AlertType.ERROR );
                return;
            }

            try {
                if (SSN_field.getText ().isEmpty () || firstNameField.getText ().isEmpty () || fatherNameField.getText ().isEmpty () || familyNameField.getText ().isEmpty () || emailField.getText ().isEmpty () || nationalityField.getText ().isEmpty () || phoneNumberTextField.getText ().isEmpty ()) {
                    HelloApplication.AlertShow ( "Please fill all fields", "Empty fields", Alert.AlertType.ERROR );
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace ();
                e.getCause ();
            }

            int SSN = Integer.parseInt ( SSN_field.getText () );



            try {
                if (phoneNumberTextField.getText ().length () != 10) {
                    HelloApplication.AlertShow ( "Please enter a valid phone number, 10 digits are allowed", "Invalid phone number", Alert.AlertType.ERROR );
                    return;
                }
                Integer.parseInt ( phoneNumberTextField.getText () );
            } catch (NumberFormatException e) {
                HelloApplication.AlertShow ( "Please enter a valid phone number, only digits (10 digits) are allowed", "Invalid phone number", Alert.AlertType.ERROR );
                return;
            }

            String firstName = firstNameField.getText ();
            String fatherName = fatherNameField.getText ();
            String familyName = familyNameField.getText ();
            String email = emailField.getText ();
            String nationality = nationalityField.getText ();
            String phoneNumber = phoneNumberTextField.getText ();

            try {
                Guest.gst = new Guest ( SSN, firstName, fatherName, familyName, email, nationality, phoneNumber );
            } catch (Exception e) {
                e.printStackTrace ();
                e.getCause ();
            }

            // guest doesn't exist, we can add.
            if (! checkIfGuestExists ( SSN )) {

                DataBaseConnection connectionDB = new DataBaseConnection ();
                connection = connectionDB.getConnection ();
                preparedStatement = connection.prepareStatement ( "insert into guest values (?,?,?,?,?,?,?)");

                preparedStatement.setInt( 1,  SSN);
                preparedStatement.setString(2,firstName);
                preparedStatement.setString(3, fatherName);
                preparedStatement.setString(4, familyName);
                preparedStatement.setString(5, email);
                preparedStatement.setString ( 6, nationality );
                preparedStatement.setString ( 7, phoneNumber );
                preparedStatement.execute ();
            }
            // guest exists already.
            else {
                HelloApplication.clearTextFields ( SSN_field );
                HelloApplication.AlertShow ( "Error, a guest with this SSN already exists", "duplicate SSN", Alert.AlertType.ERROR );
            }

            HelloApplication.clearTextFields ( SSN_field, firstNameField, familyNameField,
                    fatherNameField,nationalityField, emailField , phoneNumberTextField);

        } catch (SQLException ex) {
            ex.printStackTrace ();
            ex.getCause ();
        }
    }
    @FXML
    // update guest data
    private void deleteBtnOnAction (ActionEvent event) {

        DataBaseConnection connectDB = new DataBaseConnection ();
        String bookingDeleteQuery = "DELETE FROM booking WHERE Booking_id = " + tableView.getSelectionModel ().getSelectedItem ().getGuestSSN ();
        try {
            Connection connection = connectDB.getConnection ();

            PreparedStatement ps = connection.prepareStatement ( bookingDeleteQuery);

            Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
            alert.setContentText ( "Are you sure you want to delete the guest?" );
            alert.setHeaderText ( "Please confirm your action" );

            Optional<ButtonType> result = alert.showAndWait();

            // if the user confirms the deletion
            if ( result.isPresent () && result.get() == ButtonType.OK) {
                ps.execute ();
                HelloApplication.clearTextFields ( SSN_field, firstNameField, familyNameField,
                        fatherNameField,nationalityField, emailField , phoneNumberTextField);

            }


        } catch (Exception e) {
            e.printStackTrace ();
            e.getCause ();
        }
    }


    @FXML
    // update guest data
    private void updateBtnOnAction(ActionEvent event) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        if (tableView.getSelectionModel ().getSelectedItem () == null) {
            HelloApplication.AlertShow ( "Please select a guest to update", "No guest selected", Alert.AlertType.ERROR );
            return;
        }

        try {
                DataBaseConnection connectionDB = new DataBaseConnection ();

            try {
                if (! HelloApplication.isTextFieldEmpty (SSN_field) ) {
                    Integer.parseInt ( SSN_field.getText () );
                }
            } catch (Exception e) {
                HelloApplication.AlertShow ( "Please enter a valid SSN, only digits are allowed", "Invalid SSN", Alert.AlertType.ERROR );
                HelloApplication.clearTextFields ( SSN_field );
                return;
            }


                int SSN = HelloApplication.isTextFieldEmpty ( SSN_field ) ? tableView.getSelectionModel ().getSelectedItem ().getGuestSSN () : Integer.parseInt ( SSN_field.getText () );
                String firstName = HelloApplication.isTextFieldEmpty(firstNameField) ? tableView.getSelectionModel ().getSelectedItem ().getGuestFirstName () : firstNameField.getText ();
                String fatherName = HelloApplication.isTextFieldEmpty(fatherNameField) ? tableView.getSelectionModel ().getSelectedItem ().getGuestFatherName () : fatherNameField.getText ();
                String familyName = HelloApplication.isTextFieldEmpty(familyNameField) ? tableView.getSelectionModel ().getSelectedItem ().getGuestFamilyName () : familyNameField.getText ();
                String email = HelloApplication.isTextFieldEmpty(emailField) ? tableView.getSelectionModel ().getSelectedItem ().getGuestEmail () : emailField.getText ();
                String nationality = HelloApplication.isTextFieldEmpty(nationalityField) ? tableView.getSelectionModel ().getSelectedItem ().getGuestNationality () : nationalityField.getText ();
                String phoneNumber = HelloApplication.isTextFieldEmpty(phoneNumberTextField) ? tableView.getSelectionModel ().getSelectedItem ().getGuestPhoneNumber () : phoneNumberTextField.getText ();


            connection = connectionDB.getConnection ();
                preparedStatement = connection.prepareStatement ( "UPDATE guest G SET G.Guest_first_Name = ?," +
                        " G.Guest_father_Name = ?, G.Guest_family_Name = ?, " +
                        "G.Guest_email = ?, G.Guest_nationality = ? where Guest_SSn = ? ");



                try {
                    if (phoneNumber.length () != 10) {
                        HelloApplication.AlertShow ( "Please enter a valid phone number", "Invalid phone number", Alert.AlertType.ERROR );
                        HelloApplication.clearTextFields ( phoneNumberTextField );
                        return;
                    }

                    if (email.length () < 5 || !email.contains ( "@" ) || !email.contains ( "." ) ) {
                        HelloApplication.AlertShow ( "Please enter a valid email", "Invalid email", Alert.AlertType.ERROR );
                        HelloApplication.clearTextFields ( emailField );
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace ();
                    e.getCause ();
                }

                preparedStatement.setString ( 1, firstName);
                preparedStatement.setString ( 2, fatherName);
                preparedStatement.setString ( 3, familyName);
                preparedStatement.setString ( 4,email);
                preparedStatement.setString ( 5, nationality);
                preparedStatement.setInt ( 6, SSN);

                Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
                alert.setContentText ( "Are you sure you want to update the guest data?" );
                alert.setHeaderText ( "Please confirm your action" );

                Optional<ButtonType> result = alert.showAndWait();
                // if the user confirms the update action
                if ( result.isPresent () && result.get() == ButtonType.OK) {
                    preparedStatement.execute ();
                    tableView.refresh();
                    HelloApplication.clearTextFields ( SSN_field, firstNameField, familyNameField,
                            fatherNameField,nationalityField, emailField , phoneNumberTextField);
                }

        } catch (SQLException e) {
            e.printStackTrace ();
            e.getCause ();
        }



    }


    private boolean checkIfGuestExists(int SSN) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            DataBaseConnection connectNow = new DataBaseConnection ();
            connection = connectNow.getConnection ();
            preparedStatement = connection.prepareStatement ( "SELECT G.Guest_SSN from guest G WHERE G.Guest_SSN = ?" );
            preparedStatement.setString ( 1, SSN + "" );
            ResultSet rs = preparedStatement.executeQuery ();

            // if the result set is not empty, the guest exists.
            return rs.isBeforeFirst ();

        } catch (SQLException e) {
            e.printStackTrace ();
        }
        // if the result set is empty, the guest doesn't exist.
        return false;
    }

    @Override
    // initialize the table view
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connection = new DataBaseConnection ();
        String guestShowQuery = "SELECT * FROM Guest";

        try {

            Connection connectDB = connection.getConnection ();

            Statement statement = connectDB.createStatement ();
            ResultSet queryRes = statement.executeQuery (guestShowQuery);

            while (queryRes.next ()) {

                int SSN = queryRes.getInt ( "Guest_SSN" );
                String fname = queryRes.getString ( "Guest_first_Name" );
                String mname = queryRes.getString ( "Guest_father_Name" );
                String lname = queryRes.getString ( "Guest_family_Name" );
                String email = queryRes.getString ( "Guest_email" );
                String nationality = queryRes.getString ( "Guest_nationality" );
                String phoneNumber = queryRes.getString ( "phone_num" );

                guestObservableList.add ( new Guest ( SSN, fname, mname, lname, email, nationality, phoneNumber ));
            }
            SSNCol.setCellValueFactory ( new PropertyValueFactory<> ("guestSSN"));
            firstNameCol.setCellValueFactory ( new PropertyValueFactory<> ("guestFirstName"));
            fatherNameCol.setCellValueFactory ( new PropertyValueFactory<> ("guestFatherName"));
            familyNameCol.setCellValueFactory ( new PropertyValueFactory<> ("guestFamilyName"));
            emailCol.setCellValueFactory ( new PropertyValueFactory<> ("guestEmail"));
            nationalityCol.setCellValueFactory ( new PropertyValueFactory<> ("guestNationality"));

            tableView.setItems ( guestObservableList );

            FilteredList<Guest> filteredData = new FilteredList<> (guestObservableList, b->true);
            searchTextField.textProperty ().addListener ( (observable, oldValue, newValue)-> {
                filteredData.setPredicate ( guest -> {

                    if (newValue == null || newValue.isEmpty () || newValue.isBlank ()) {
                        return true;
                    }

                    String searchKeywords = newValue.toLowerCase ();

                    if (guest.getGuestFirstName ().toLowerCase ().contains ( searchKeywords )) {
                        return true;
                    }
                    if  (guest.getGuestFatherName ().toLowerCase ().contains ( searchKeywords )) {
                        return true;
                    }
                    if (guest.getGuestFamilyName ().toLowerCase ().contains ( searchKeywords )) {
                        return true;
                    }
                    if (guest.getGuestEmail ().toLowerCase ().contains ( searchKeywords )) {
                        return true;
                    }
                    if (guest.getGuestNationality ().toLowerCase ().contains ( searchKeywords )) {
                        return true;
                    }
                    return (guest.getGuestSSN () + "").toLowerCase ().contains ( searchKeywords );
                } );
            } );

            // wrap the FilteredList in a SortedList.
            SortedList <Guest> sortedList = new SortedList<> ( filteredData );
            sortedList.comparatorProperty ().bind ( tableView.comparatorProperty () );
            tableView.setItems ( filteredData );


        } catch (SQLException ex) {
            Logger.getLogger (GuestController.class.getName ()).log( Level.SEVERE, null, ex );
            ex.printStackTrace ();
        }
        catch (Exception e) {
            e.printStackTrace ();
        }



    }
}



