package com.example.comp333;

import javax.xml.transform.Result;
import java.sql.*;

public class Tmp {
    public static void main(String[] args) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_comp333", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM EMPLOYEE");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("eid"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


}
