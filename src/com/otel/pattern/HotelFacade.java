package com.otel.pattern;

import com.otel.db.DatabaseConnection;
import com.otel.model.Customer;
import com.otel.model.BaseRoom;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class HotelFacade {

    // Rezervasyon Yapma (Giriş-Çıkış Tarihi eklendi)
    public boolean placeReservation(BaseRoom baseRoom, String userName, String tcNo, LocalDate inDate, LocalDate outDate) {
        // 1. Odayı Rezerve Et (State Pattern)
        boolean success = baseRoom.makeReservation();

        if (success) {
            // --- GÜN SAYISI VE TOPLAM TUTAR HESABI ---
            long gunSayisi = java.time.temporal.ChronoUnit.DAYS.between(inDate, outDate);
            if (gunSayisi < 1) gunSayisi = 1; // En az 1 gün

            double toplamTutar = baseRoom.getPrice() * gunSayisi;

            // 2. Veritabanına Kayıt At (Artık TOPLAM TUTARI kaydediyoruz)
            saveReservationToDB(tcNo, baseRoom.getRoomNumber(), inDate, outDate, toplamTutar);

            // 3. Bildirim Gönder
            ReservationNotifier notifier = new ReservationNotifier();
            com.otel.model.Customer customer = new com.otel.model.Customer(0, userName, tcNo);
            notifier.addObserver(customer);
            notifier.notifyObservers("Oda " + baseRoom.getRoomNumber() + " rezerve edildi. Tutar: " + toplamTutar + " TL");

            // 4. Fiş Oluştur (Builder)
            ReservationTicket ticket = new ReservationTicket.TicketBuilder("Kırklareli Otel", userName)
                    .setRoomNumber(baseRoom.getRoomNumber())
                    .setPrice(toplamTutar) // Fişe de toplam tutarı yaz
                    .build();
            System.out.println(ticket);
        }
        return success;
    }

    // Rezervasyon İptal Etme (YENİ)
    // Rezervasyon İptal Etme
    public boolean cancelReservation(int reservationId, int roomNo) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            // A. Rezervasyon Durumunu 'IPTAL' yap (Geçmişte görünsün diye silmiyoruz, güncelliyoruz)
            String sqlRes = "UPDATE reservations SET durum = 'IPTAL' WHERE id = ?";
            PreparedStatement pstRes = conn.prepareStatement(sqlRes);
            pstRes.setInt(1, reservationId);
            pstRes.executeUpdate();

            // --- İŞTE BURASI ODANIN DURUMUNU DÜZELTİYOR ---
            // B. Odayı Tekrar 'MUSAIT' yap
            String sqlRoom = "UPDATE rooms SET durum = 'MUSAIT' WHERE oda_no = ?";
            PreparedStatement pstRoom = conn.prepareStatement(sqlRoom);
            pstRoom.setInt(1, roomNo); // Hangi oda olduğunu parametre olarak alıyoruz
            pstRoom.executeUpdate();
            // -----------------------------------------------

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // Personel Onayı (Check-In)
    public boolean approveCheckIn(BaseRoom room) {
        // State Pattern üzerinden giriş işlemini tetikle
        return room.checkIn();
    }

    private void saveReservationToDB(String tc, int roomNo, LocalDate in, LocalDate out, double price) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO reservations (tc_no, oda_no, giris_tarihi, cikis_tarihi, ucret, durum) VALUES (?, ?, ?, ?, ?, 'AKTIF')";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tc);
            pstmt.setInt(2, roomNo);
            pstmt.setDate(3, Date.valueOf(in));
            pstmt.setDate(4, Date.valueOf(out));
            pstmt.setDouble(5, price);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- ÖDEME İŞLEMİ (YENİ) ---
    public boolean confirmPayment(int reservationId) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "UPDATE reservations SET odeme_durumu = 'ODENDI' WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, reservationId);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}