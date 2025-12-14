package com.otel.pattern;

// Bu sınıf Builder Pattern kullanılarak oluşturulacak
public class ReservationTicket {
    // Zorunlu alanlar
    private String hotelName;
    private String customerName;

    // Opsiyonel alanlar
    private int roomNumber;
    private double price;
    private String note;

    // Gizli Constructor (Sadece Builder erişsin diye)
    private ReservationTicket(TicketBuilder builder) {
        this.hotelName = builder.hotelName;
        this.customerName = builder.customerName;
        this.roomNumber = builder.roomNumber;
        this.price = builder.price;
        this.note = builder.note;
    }

    @Override
    public String toString() {
        return "====================================\n" +
                "REZERVASYON FİŞİ\n" +
                "Otel: " + hotelName + "\n" +
                "Müşteri: " + customerName + "\n" +
                "Oda No: " + roomNumber + "\n" +
                "Tutar: " + price + " TL\n" +
                "Not: " + (note != null ? note : "-") + "\n" +
                "====================================";
    }

    // --- STATIC INNER CLASS (BUILDER) ---
    public static class TicketBuilder {
        private String hotelName;
        private String customerName;
        private int roomNumber;
        private double price;
        private String note;

        public TicketBuilder(String hotelName, String customerName) {
            this.hotelName = hotelName;
            this.customerName = customerName;
        }

        public TicketBuilder setRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public TicketBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public TicketBuilder setNote(String note) {
            this.note = note;
            return this;
        }

        public ReservationTicket build() {
            return new ReservationTicket(this);
        }
    }
}