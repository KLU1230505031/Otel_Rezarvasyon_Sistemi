package com.otel.pattern;

import com.otel.model.*;

public class RoomFactory {

    public static BaseRoom createRoom(String type, int roomNumber, double price, int capacity, String statusStr) {
        // Durum (State) nesnesini belirle
        RoomState state;
        if ("DOLU".equalsIgnoreCase(statusStr)) {
            state = new OccupiedState();
        } else if ("REZERVE".equalsIgnoreCase(statusStr)) {
            state = new ReservedState();
        } else {
            state = new AvailableState(); // Varsayılan MUSAIT
        }

        // Tip ismine göre doğru sınıfı üret (Polimorfizm)
        switch (type.toUpperCase()) {
            case "SUIT":
                return new SuitRoom(roomNumber, price, type, capacity, state);
            case "AILE":
                return new AileRoom(roomNumber, price, type, capacity, state);
            case "EKONOMIK":
                return new EkonomikRoom(roomNumber, price, type, capacity, state);
            case "DELUXE":
                return new DeluxeRoom(roomNumber, price, type, capacity, state);
            case "KRAL":
                return new KralRoom(roomNumber, price, type, capacity, state);
            case "STANDART":
            default:
                // Tanımsız bir tip gelirse varsayılan olarak Standart üretir
                return new StandartRoom(roomNumber, price, type, capacity, state);
        }
    }
}