package com.otel.pattern;
import com.otel.model.BaseRoom;

public class OccupiedState implements RoomState {

    @Override
    public void handleStatus(BaseRoom baseRoom) {
        System.out.println("Bu oda dolu!");
    }

    @Override
    public String getStatusName() {
        return "DOLU";
    }

    @Override
    public boolean checkIn(BaseRoom baseRoom) {
        System.out.println("HATA: Oda zaten dolu, tekrar giriş yapılamaz.");
        return false;
    }

    @Override
    public boolean reserve(BaseRoom baseRoom) {
        // Dolu odaya rezervasyon yapılamaz
        System.out.println("HATA: Oda " + baseRoom.getRoomNumber() + " zaten dolu!");
        return false;
    }
}