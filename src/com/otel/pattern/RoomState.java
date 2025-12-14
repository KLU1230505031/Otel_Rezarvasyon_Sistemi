package com.otel.pattern;
import com.otel.model.BaseRoom;

public interface RoomState {
    void handleStatus(BaseRoom baseRoom);
    String getStatusName();

    // Yeni eklediğimiz metod: Rezervasyon yapmaya çalışır
    // Başarılı olursa true, başarısızsa false döner
    boolean reserve(BaseRoom baseRoom);

    boolean checkIn(BaseRoom baseRoom);
}