package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;


public class loginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    Button loginButton;
    @FXML
    Button clearButton;
    @FXML
    Label tryAgainLabel;

    @FXML
    private  ActionEvent event;


    public void clearButtonOnAction(ActionEvent event) {
        tryAgainLabel.setText("");
        userName.setText("");
        password.setText("");
    }


    public void loginButtonOnAction(ActionEvent event) throws SQLException {
        if (!userName.getText().isBlank() && !password.getText().isBlank()) { // if username and password are not blank
            validateLogin(event); // call validateLogin method
        } else {
            tryAgainLabel.setText("PLEASE INPUT USERNAME AND PASSWORD"); // if username and password are blank
            tryAgainLabel.setTextFill(Color.RED);
        }
    }


        public void validateLogin (ActionEvent event) throws SQLException { // parameter added

            DataBaseConnection connectNow = new DataBaseConnection (); // create new object of DataBaseConnection class
            Connection connectDB = connectNow.getConnection ();
            String verifyLogin = "";
            try {
                verifyLogin = "SELECT EID FROM EMPLOYEE WHERE  EID = " + userName.getText() + " AND PASSWORD =  '" + password.getText() + "'"; // query to check if username and password are correct
            } catch (Exception e) {
                HelloApplication.AlertShow("Username should be Integer", "Error", Alert.AlertType.ERROR);
                return;
            }
            try {

                Statement statement = connectDB.createStatement (); // create statement
                ResultSet queryResult;
                try {
                    queryResult = statement.executeQuery ( verifyLogin ); // execute query
                } catch (Exception e) {
                    HelloApplication.AlertShow("Username should be Integer", "Error", Alert.AlertType.ERROR);
                    return;
                }


                if (queryResult.next ()) { // if query returned result (upd by Hamza)
                    System.out.println ( "logged in successfully" );
                    tryAgainLabel.setText ( "WELCOME" );
                    tryAgainLabel.setTextFill ( Color.GREEN );

                    HelloApplication.changeScene (event, "MenuScene.fxml","Hotel DataBase!" ,600,562 );

                } else {
                    tryAgainLabel.setText ( "Please enter valid username and password" );
                    tryAgainLabel.setTextFill ( Color.RED );
                    HelloApplication.AlertShow ( "WRONG USERNAME OR PASSWORD", "Error", Alert.AlertType.ERROR );
                }


            } catch (Exception e) {
                e.printStackTrace ();
                e.getCause ();
            }
        }
    }
