package com.example.comp333;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    public void clearButtonOnAction(ActionEvent event) {
        userName.setText("");
        password.setText("");
    }


    public void loginButtonOnAction(ActionEvent event) throws SQLException { //throws IOException
        if (!userName.getText().isBlank() && !password.getText().isBlank()) { // if username and password are not blank
            validateLogin(); // call validateLogin method
        } else {
            tryAgainLabel.setText("PLEASE INPUT USERNAME AND PASSWORD"); // if username and password are blank
            tryAgainLabel.setTextFill(Color.RED);
        }
    }

    public void validateLogin() throws SQLException {

        DataBaseConnection connectNow = new DataBaseConnection(); // create new object of DataBaseConnection class
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT EID FROM EMPLOYEE WHERE  EID = '" + userName.getText() + "' AND PASSWORD =  '" + password.getText() + "'"; // query to check if username and password are correct

        try {

            Statement statement = connectDB.createStatement(); // create statement
            ResultSet queryResult = statement.executeQuery(verifyLogin); // execute query

            if (queryResult.next()) { // if query returned result (upd by Hamza)
                System.out.println("logged in successfully");
                tryAgainLabel.setText("WELCOME");
                tryAgainLabel.setTextFill(Color.GREEN);
                /*
                    SWITCH SCENE HERE

                */
            } else {
                tryAgainLabel.setText("WRONG USERNAME OR PASSWORD TRY AGAIN");
                tryAgainLabel.setTextFill(Color.RED);
                System.out.println("try again"); // if username and password are incorrect
            }

        } catch (Exception e) { //
            tryAgainLabel.setText("WRONG USERNAME OR PASSWORD TRY AGAIN");
            tryAgainLabel.setTextFill(Color.RED);
            e.printStackTrace();
            e.getCause();
        }

    }


}
