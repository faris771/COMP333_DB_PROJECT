package com.example.comp333;

public class Service {
    private int serviceID;
    private String serviceType;
    private double servicePrice;


    public static Service service; // object to do operations

    public Service(int serviceID, String serviceName, double servicePrice) {
        this.serviceID = serviceID;
        this.serviceType = serviceName;
        this.servicePrice = servicePrice;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }


    @Override
    public String toString() {
        return "Service{" +
                "serviceID=" + serviceID +
                ", serviceName='" + serviceType + '\'' +
                ", servicePrice=" + servicePrice +
                '}';
    }


}

