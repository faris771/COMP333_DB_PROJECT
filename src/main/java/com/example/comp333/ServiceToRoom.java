package com.example.comp333;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ServiceToRoom {

    private int serviceID;
    private int roomNumber;
    private int employeeID;
    private String serviceDate;

    public ServiceToRoom(int serviceID, int roomNumber, int employeeID, String serviceDate) {
        this.serviceID = serviceID;
        this.roomNumber = roomNumber;
        this.employeeID = employeeID;
        this.serviceDate = serviceDate;
    }


    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }
}
