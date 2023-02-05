package com.example.comp333;

public class Phone {


    private int employeeID;
    private String phoneNumber;

    public Phone( String phoneNumber, int employeeID) {
        this.employeeID = employeeID;
        this.phoneNumber = phoneNumber;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
