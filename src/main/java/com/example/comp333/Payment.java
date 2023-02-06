package com.example.comp333;

public class Payment {
    int SSN;
    int paymentID;
    String paymentType; // card or cash
    String paymentDate;
    double amountPaid;

public Payment(int SSN, String paymentType, String paymentDate, double amountPaid, int paymentID) {
        this.SSN = SSN;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentID = paymentID;
    }

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "SSN=" + SSN +
                ", paymentType='" + paymentType + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", amountPaid=" + amountPaid +
                '}';
    }
}
