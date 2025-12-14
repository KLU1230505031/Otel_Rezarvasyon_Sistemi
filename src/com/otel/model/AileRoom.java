package com.otel.model;
import com.otel.pattern.RoomState;

public class AileRoom extends BaseRoom {
    public AileRoom(int roomNumber, double price, String type, int capacity, RoomState state) {
        super(roomNumber, price, type, capacity, state);
    }
    @Override
    public String getDescription() { return "Geniş aileler için " + type + " odası."; }
}