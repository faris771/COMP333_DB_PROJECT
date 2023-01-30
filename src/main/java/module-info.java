module com.example.comp333 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.comp333 to javafx.fxml;
    exports com.example.comp333;
}