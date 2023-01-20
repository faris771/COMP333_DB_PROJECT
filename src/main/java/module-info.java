module com.example.comp333 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.comp333 to javafx.fxml;
    exports com.example.comp333;
}