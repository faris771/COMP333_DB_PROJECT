package com.example.comp333;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public Connection dataBaseLink;

    public Connection getConnection() throws SQLException {
        try {
            dataBaseLink = DriverManager.getConnection ( "jdbc:mysql://localhost:3306/hotel_comp333", "root", "1200373" ); //THIS DIFFERS FROM USER TO ANOTHER CHANGE IT
        }catch (Exception e) {
            e.printStackTrace ();
        }
        return dataBaseLink;
    }
}
