package com.otel.pattern;

import com.otel.db.DatabaseConnection;
import com.otel.model.BaseRoom;
import java.sql.PreparedStatement;

public class AvailableState implements RoomState {



    @Override
    public void handleStatus(BaseRoom baseRoom) {
        System.out.println("Oda şu an müsait.");
    }

    @Override
    public String getStatusName() {
        return "MUSAIT";
    }

    @Override
    public boolean checkIn(BaseRoom baseRoom) {
        System.out.println("HATA: Önce rezervasyon yapılmalı!");
        return false;
    }

    @Override
    public boolean reserve(BaseRoom baseRoom) {
        try {
            // 1. Veritabanında durumu 'REZERVE' yap (Eskiden 'DOLU' yapıyorduk)
            String sql = "UPDATE rooms SET durum = 'REZERVE' WHERE oda_no = ?";
            PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            pstmt.setInt(1, baseRoom.getRoomNumber());
            int result = pstmt.executeUpdate();

            if (result > 0) {
                // 2. Nesnenin durumunu ReservedState'e çek
                baseRoom.setState(new ReservedState());
                System.out.println("Oda " + baseRoom.getRoomNumber() + " başarıyla rezerve durumuna alındı.");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}