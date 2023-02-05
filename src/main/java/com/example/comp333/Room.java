package com.example.comp333;

public class Room {
    private int roomNumber;
    private double roomPrice;
    private int roomNumberOfBeds;
    private String roomType;
    private String roomStatus;

    public static Room room;

    public Room(int roomNumber, double roomPrice, int roomNumberOfBeds, String roomType, String roomStatus) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomNumberOfBeds = roomNumberOfBeds;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
    }


    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getRoomNumberOfBeds() {
        return roomNumberOfBeds;
    }

    public void setRoomNumberOfBeds(int roomNumberOfBeds) {
        this.roomNumberOfBeds = roomNumberOfBeds;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomPrice=" + roomPrice +
                ", roomNumberOfBeds=" + roomNumberOfBeds +
                ", roomType='" + roomType + '\'' +
                ", roomStatus='" + roomStatus + '\'' +
                '}';
    }

}

