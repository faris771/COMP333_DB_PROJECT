package com.example.comp333;

public class Guest {
    private int guestSSN;
    private String guestPhoneNumber;
    private String guestFirstName;
    private String guestFatherName;
    private String guestFamilyName;
    private String guestEmail;
    private String guestNationality;

    static Guest gst;

    public Guest(int guestSSN, String guestFirstName, String guestFatherName, String guestFamilyName, String guestEmail, String guestNationality, String guestPhoneNumber) {

        this.guestSSN = guestSSN;
        this.guestFirstName = guestFirstName;
        this.guestFatherName = guestFatherName;
        this.guestFamilyName = guestFamilyName;
        this.guestEmail = guestEmail;
        this.guestNationality = guestNationality;
        setGuestPhoneNumber ( guestPhoneNumber );
    }

    public int getGuestSSN() {
        return guestSSN;
    }

    public String getGuestPhoneNumber() {
        return guestPhoneNumber;
    }

    public void setGuestPhoneNumber(String guestPhoneNumber) {
        try  {
            if (guestPhoneNumber.length() != 10) {
                throw new Exception("Phone number must be 10 digits");
            }
            if (guestPhoneNumber.charAt(0) != '0') {
                throw new Exception("Phone number must start with 0");
            }
            Integer.parseInt ( guestPhoneNumber );

        } catch (Exception e) {
            throw new IllegalArgumentException("Phone number must be 10 digits and start with 0, and consist of numbers only");
        }
        this.guestPhoneNumber = guestPhoneNumber;
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

    @Override
    public String toString() {
        return "Guest{" +
                "guestSSN=" + guestSSN +
                ", guestFirstName='" + guestFirstName + '\'' +
                ", guestFatherName='" + guestFatherName + '\'' +
                ", guestFamilyName='" + guestFamilyName + '\'' +
                ", guestEmail='" + guestEmail + '\'' +
                ", guestNationality='" + guestNationality + '\'' + '}';
    }


}
