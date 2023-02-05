package com.example.comp333;

public class Phone {

    private int SSN;
    private String phoneNumber;

    public Phone(int SSN, String phoneNumber) {
        this.SSN = SSN;
        this.phoneNumber = phoneNumber;
    }

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
