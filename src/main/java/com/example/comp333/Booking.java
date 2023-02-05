package com.example.comp333;

import java.util.Date;

public class Booking {
    private int SSN;
    private int ID;
    private String roomNumber;
    private String startDate;
    private String endDate;

    public Booking(int SSN, int ID, String roomNumber, String startDate, String endDate) {
        this.SSN = SSN;
        this.ID = ID;
        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    @Override
    public String toString() {
        return "Booking [SSN=" + SSN + ", endDate=" + endDate + ", roomNumber=" + roomNumber + ", startDate=" + startDate
                + "]";
    }
}
