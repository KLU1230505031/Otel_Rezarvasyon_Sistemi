package com.otel.model;
import com.otel.pattern.RoomState;

public abstract class BaseRoom {
    protected int roomNumber;
    protected double price;
    protected String type;
    protected int capacity; // YENİ EKLENEN ALAN
    protected RoomState state;

    // Constructor güncellendi
    public BaseRoom(int roomNumber, double price, String type, int capacity, RoomState state) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.type = type;
        this.capacity = capacity;
        this.state = state;
    }

    public abstract String getDescription();

    // Getter Metodları
    public int getRoomNumber() { return roomNumber; }
    public double getPrice() { return price; }
    public String getType() { return type; }
    public int getCapacity() { return capacity; } // YENİ GETTER
    public String getStateName() { return state.getStatusName(); }

    public void setState(RoomState state) { this.state = state; }

    // Rezervasyon metodu (Aynen kalıyor)
    public boolean makeReservation() {
        return state.reserve(this);
    }
    public boolean checkIn() {
        return state.checkIn(this);
    }


    public com.otel.pattern.RoomState getRoomState() {
        return state;
    }
}