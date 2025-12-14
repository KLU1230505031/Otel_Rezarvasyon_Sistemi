package com.otel.model;
import com.otel.pattern.RoomState;

public class SuitRoom extends BaseRoom {
    public SuitRoom(int roomNumber, double price, String type, int capacity, RoomState state) {
        super(roomNumber, price, type, capacity, state);
    }
    @Override
    public String getDescription() { return "Lüks ve geniş " + type + " odası."; }
}