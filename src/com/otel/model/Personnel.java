package com.otel.model;

public class Personnel extends User {
    public Personnel(int id, String adSoyad, String tcNo) {
        super(id, adSoyad, tcNo);
    }

    @Override
    public void openDashboard() {
        System.out.println("Personel Paneli");
    }
}