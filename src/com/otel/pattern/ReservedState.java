package com.otel.pattern;

import com.otel.db.DatabaseConnection;
import com.otel.model.BaseRoom;
import java.sql.PreparedStatement;

public class ReservedState implements RoomState {

    @Override
    public void handleStatus(BaseRoom baseRoom) {
        System.out.println("Oda rezerve. Müşteri bekleniyor.");
    }

    @Override
    public String getStatusName() {
        return "REZERVE";
    }

    @Override
    public boolean reserve(BaseRoom baseRoom) {
        System.out.println("HATA: Oda zaten rezerve!");
        return false;
    }

    // --- YENİ EKLENEN KISIM: ONAYLAMA İŞLEMİ ---
    @Override
    public boolean checkIn(BaseRoom baseRoom) {
        try {
            // 1. Veritabanında durumu 'DOLU' yap
            String sql = "UPDATE rooms SET durum = 'DOLU' WHERE oda_no = ?";
            PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
            pstmt.setInt(1, baseRoom.getRoomNumber());
            int result = pstmt.executeUpdate();

            if (result > 0) {
                // 2. State'i değiştir (Dolu yap)
                baseRoom.setState(new OccupiedState());
                System.out.println("Oda " + baseRoom.getRoomNumber() + " için giriş onayı verildi. Artık DOLU.");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}