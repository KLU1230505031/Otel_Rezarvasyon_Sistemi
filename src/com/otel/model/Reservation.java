package com.otel.model;

import java.sql.Date;

public class Reservation {
    private int id;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;
    private double price;
    private String status;
    private String paymentStatus;

    // --- YENİ EKLENEN ALAN ---
    private String customerName;

    public Reservation(int id, int roomNumber, Date checkIn, Date checkOut, double price, String status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.price = price;
        this.status = status;
    }

    // --- GETTER VE SETTER (YENİSİ EKLENDİ) ---
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public int getId() { return id; }
    public int getRoomNumber() { return roomNumber; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
}