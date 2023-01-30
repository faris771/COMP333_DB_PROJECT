package com.example.comp333;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public Connection dataBaseLink;

    public Connection getConnection() throws SQLException {

        dataBaseLink = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_comp333", "root", "password");
        return dataBaseLink;

    }
}
