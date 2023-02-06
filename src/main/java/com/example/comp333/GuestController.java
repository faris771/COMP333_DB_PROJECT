package com.example.comp333;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.channels.AcceptPendingException;
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            int SSN = Integer.parseInt ( SSN_field.getText () );
            // guest exists in the database
            if (checkIfGuestExists ( SSN )) {
                connection = DriverManager.getConnection ( "jdbc:mysql://localhost:3306/hotel_comp333", "root", "password" );

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
            Guest.gst = new Guest ( Integer.parseInt ( SSN_field.getText () ), firstNameField.getText (),
                    fatherNameField.getText (), familyNameField.getText (), emailField.getText (), nationalityField.getText ());

            // guest doesn't exist, we can add.
            if (! checkIfGuestExists ( Guest.gst.getGuestSSN () )) {

                connection = DriverManager.getConnection ( "jdbc:mysql://localhost:3306/hotel_comp333", "root", "password" );
                preparedStatement = connection.prepareStatement ( "insert into guest values (?,?,?,?,?,?)");

                preparedStatement.setString ( 1, Guest.gst.getGuestSSN () + "" );
                preparedStatement.setString(2, Guest.gst.getGuestFirstName ());
                preparedStatement.setString(3, Guest.gst.getGuestFatherName ());
                preparedStatement.setString(4, Guest.gst.getGuestFamilyName ());
                preparedStatement.setString(5, Guest.gst.getGuestEmail ());
                preparedStatement.setString ( 6, Guest.gst.getGuestNationality () );
                preparedStatement.execute ();
                tableView.refresh();
            }
            // guest exists already.
            else {
                HelloApplication.clearTextFields ( SSN_field );
                HelloApplication.AlertShow ( "Error, a guest with this SSN already exists", "duplicate SSN", Alert.AlertType.ERROR );
            }

            HelloApplication.clearTextFields ( SSN_field, firstNameField, familyNameField,
                    fatherNameField,nationalityField, emailField  );

        } catch (SQLException ex) {
            ex.printStackTrace ();
            ex.getCause ();
        }
    }
    @FXML
    // update guest data
    private void deleteBtnOnAction (ActionEvent event) {

        DataBaseConnection connection = new DataBaseConnection ();
        String bookingDeleteQuery = "DELETE FROM booking WHERE Booking_id = " + tableView.getSelectionModel ().getSelectedItem ().getGuestSSN ();
        try {
            Connection connectDB = connection.getConnection ();
            Statement statement = connectDB.createStatement ();

            PreparedStatement ps = connectDB.prepareStatement ( bookingDeleteQuery);

            Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
            alert.setContentText ( "Are you sure you want to delete the guest?" );
            alert.setHeaderText ( "Please confirm your action" );

            Optional<ButtonType> result = alert.showAndWait();

            // if the user confirms the deletion
            if ( result.isPresent () && result.get() == ButtonType.OK) {
                ps.execute ();
                tableView.refresh();
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
        try {

            connection = DriverManager.getConnection ( "jdbc:mysql://localhost:3306/hotel_comp333", "root", "password" );
                preparedStatement = connection.prepareStatement ( "UPDATE guest G SET G.Guest_first_Name = ?," +
                        " G.Guest_father_Name = ?, G.Guest_family_Name = ?, " +
                        "G.Guest_email = ?, G.Guest_nationality = ? where Guest_SSn = ? ");

                preparedStatement.setString ( 1, firstNameField.getText ());
                preparedStatement.setString ( 2, fatherNameField.getText ());
                preparedStatement.setString ( 3, familyNameField.getText ());
                preparedStatement.setString ( 4, emailField.getText ());
                preparedStatement.setString ( 5, nationalityField.getText ());
                preparedStatement.setInt ( 6, Integer.parseInt ( SSN_field.getText () ));

                Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
                alert.setContentText ( "Are you sure you want to update the guest data?" );
                alert.setHeaderText ( "Please confirm your action" );

                Optional<ButtonType> result = alert.showAndWait();
                // if the user confirms the update action
                if ( result.isPresent () && result.get() == ButtonType.OK) {
                    preparedStatement.execute ();
                    tableView.refresh();

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
            connection = DriverManager.getConnection ( "jdbc:mysql://localhost:3306/hotel_comp333", "root", "password" );
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

                guestObservableList.add ( new Guest ( SSN, fname, mname, lname, email, nationality ));
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



