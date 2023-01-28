package com.example.comp333;

public class Guest {
    private int guestSSN;
    private String guestFirstName;
    private String guestFatherName;
    private String guestFamilyName;
    private String guestEmail;
    private String guestNationality;
    private String guestPhone; //single phone number

    public Guest(int guestSSN, String guestFirstName, String guestFatherName, String guestFamilyName, String guestEmail, String guestNationality, String guestPhone) {

        this.guestSSN = guestSSN;
        this.guestFirstName = guestFirstName;
        this.guestFatherName = guestFatherName;
        this.guestFamilyName = guestFamilyName;
        this.guestEmail = guestEmail;
        this.guestNationality = guestNationality;
        this.guestPhone = guestPhone;
    }

    public int getGuestSSN() {
        return guestSSN;
    }

    public void setGuestSSN(int guestSSN) {
        this.guestSSN = guestSSN;
    }

    public String getGuestFirstName() {
        return guestFirstName;
    }

    public void setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
    }

    public String getGuestFatherName() {
        return guestFatherName;
    }

    public void setGuestFatherName(String guestFatherName) {
        this.guestFatherName = guestFatherName;
    }

    public String getGuestFamilyName() {
        return guestFamilyName;
    }

    public void setGuestFamilyName(String guestFamilyName) {
        this.guestFamilyName = guestFamilyName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getGuestNationality() {
        return guestNationality;
    }

    public void setGuestNationality(String guestNationality) {
        this.guestNationality = guestNationality;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "guestSSN=" + guestSSN +
                ", guestFirstName='" + guestFirstName + '\'' +
                ", guestFatherName='" + guestFatherName + '\'' +
                ", guestFamilyName='" + guestFamilyName + '\'' +
                ", guestEmail='" + guestEmail + '\'' +
                ", guestNationality='" + guestNationality + '\'' +
                ", guestPhone='" + guestPhone + '\'' +
                '}';
    }


}
