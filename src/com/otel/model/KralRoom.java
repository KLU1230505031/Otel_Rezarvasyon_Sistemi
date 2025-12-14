package com.otel.model;
import com.otel.pattern.RoomState;

public class KralRoom extends BaseRoom {
    public KralRoom(int roomNumber, double price, String type, int capacity, RoomState state) {
        super(roomNumber, price, type, capacity, state);
    }
    @Override
    public String getDescription() { return "En üst düzey lüks " + type + " dairesi."; }
}