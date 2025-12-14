package com.otel.pattern;

import java.util.ArrayList;
import java.util.List;

public class ReservationNotifier {
    private List<Observer> observers = new ArrayList<>();

    // Listeye gözlemci (Müşteri) ekle
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Listeden çıkar
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Herkese bildirim gönder
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}