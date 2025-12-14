package com.otel.model;
import com.otel.pattern.RoomState;

public class DeluxeRoom extends BaseRoom {
    public DeluxeRoom(int roomNumber, double price, String type, int capacity, RoomState state) {
        super(roomNumber, price, type, capacity, state);
    }
    @Override
    public String getDescription() { return "Ekstra konforlu " + type + " odası."; }
}