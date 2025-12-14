package com.otel.model;

import com.otel.pattern.Observer;

public class Customer extends User implements Observer {

    // Yeni eklenen alanlar
    private String email;
    private String telefon;

    // Constructor güncellendi: Artık email ve telefon da alıyor
    public Customer(int id, String adSoyad, String tcNo, String email, String telefon) {
        super(id, adSoyad, tcNo);
        this.email = email;
        this.telefon = telefon;
    }

    // Eski kodların bozulmaması için (eğer sadece ID, Ad, TC ile çağrılan yerler varsa)
    // bu ikinci constructor'ı ekleyebiliriz (Opsiyonel ama tavsiye edilir)
    public Customer(int id, String adSoyad, String tcNo) {
        super(id, adSoyad, tcNo);
    }

    // --- GETTER METODLARI ---
    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    @Override
    public void openDashboard() {
        // İleride gerekirse doldurulur
    }

    @Override
    public void update(String message) {
        System.out.println("------------------------------------------------");
        System.out.println("SAYIN " + this.adSoyad.toUpperCase() + ", YENİ BİLDİRİMİNİZ VAR:");
        System.out.println(">> " + message);
        System.out.println("------------------------------------------------");
    }
}