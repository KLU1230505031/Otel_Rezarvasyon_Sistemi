package com.otel.model;
import com.otel.pattern.RoomState;

public class EkonomikRoom extends BaseRoom {
    public EkonomikRoom(int roomNumber, double price, String type, int capacity, RoomState state) {
        super(roomNumber, price, type, capacity, state);
    }
    @Override
    public String getDescription() { return "Bütçe dostu " + type + " odası."; }
}