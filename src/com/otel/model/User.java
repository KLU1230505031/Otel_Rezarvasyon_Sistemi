package com.otel.model;

public abstract class User {
    protected int id;
    protected String adSoyad;
    protected String tcNo;

    public User(int id, String adSoyad, String tcNo) {
        this.id = id;
        this.adSoyad = adSoyad;
        this.tcNo = tcNo;
    }

    // --- EKLENMESİ GEREKEN GETTER METODLARI ---

    public String getTcNo() {
        return tcNo;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public int getId() {
        return id;
    }

    // Abstract metod (Aynen kalıyor)
    public abstract void openDashboard();
}