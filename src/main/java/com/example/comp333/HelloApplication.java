package com.example.comp333;
// hamza branch
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
// Hamza Awashra
import java.io.IOException;
// testing pull
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LogInScene.fxml"));
        // for the login scene put the size  410 , 500
        // for menu Scene put the size 500 , 600
        // for guest scene use the size 760 , 600
        // for Services scene use the size 760 , 600
        Scene scene = new Scene(fxmlLoader.load(),  410,500);
        stage.setTitle("Hotel DataBase!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(e->{
         //   e.consume();
            logout(stage);
        });
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("log out");
        alert.setHeaderText("You are about to logout");


        alert.setContentText("Are you sure you want to quit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("successfully logged out");
            stage.close();
        }

    }

    public static void changeScene(ActionEvent event, String fxml_file, String title , int height , int width) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader( HelloApplication.class.getResource(fxml_file));
            Stage stage1 = (Stage) ((Node) event.getSource ()).getScene ().getWindow ();
            // for log in Scene put the size 760 , 600
            Scene scene1 = new Scene ( fxmlLoader.load (), height, width);
            stage1.setTitle(title);
            stage1.setScene( scene1 );
            stage1.setResizable(false);
            stage1.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void AlertShow(String text, String title, Alert.AlertType  type) {

        Alert alert = new Alert (type);
        alert.setContentText ( text );
        alert.setTitle ( title );
        alert.show ();

    }


    public static void main(String[] args) {
        launch();
    }
    public static void clearTextFields(TextField... fields) {
        for (TextField f : fields) {
            f.clear ();
        }
    }
}

