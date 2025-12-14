package com.otel.model;

import com.otel.pattern.RoomState;

public class StandartRoom extends BaseRoom {
    // Constructor artık 'type' (tip) bilgisini de alıyor
    public StandartRoom(int roomNumber, double price, String type, int capacity, RoomState state) {
        super(roomNumber, price, type, capacity, state); // Gelen tipi üst sınıfa (Room) iletiyor
    }

    @Override
    public String getDescription() {
        return "Oda Tipi: " + type;
    }
}