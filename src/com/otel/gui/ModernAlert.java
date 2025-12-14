package com.otel.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModernAlert {

    public static void show(String title, String message, Alert.AlertType type) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Arkadaki pencereye tıklanmasını engeller
        stage.initStyle(StageStyle.TRANSPARENT); // Pencere kenarlıkları olmasın (Windows çerçevesi yok)

        // --- KART TASARIMI ---
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25));

        // Beyaz ve Yuvarlak Köşeli
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), Insets.EMPTY)));

        // Hafif bir gölge verelim ki havada duruyor gibi olsun
        card.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.25)));

        // Kenar çizgisi (isteğe bağlı, daha belirgin olması için)
        card.setStyle("-fx-border-color: #ecf0f1; -fx-border-width: 1; -fx-border-radius: 20; -fx-background-radius: 20;");

        // --- RENK VE İKON AYARLARI ---
        String headerColor;
        String btnColor;

        if (type == Alert.AlertType.ERROR) {
            headerColor = "#d63031"; // Kırmızı
            btnColor = "#d63031";
        } else if (type == Alert.AlertType.WARNING) {
            headerColor = "#e17055"; // Turuncu
            btnColor = "#e17055";
        } else {
            headerColor = "#00b894"; // Yeşil (Success/Info)
            btnColor = "#00b894";
        }

        // Başlık
        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setTextFill(Color.web(headerColor));

        // Mesaj
        Label lblMsg = new Label(message);
        lblMsg.setFont(Font.font("Segoe UI", 14));
        lblMsg.setWrapText(true);
        lblMsg.setTextAlignment(TextAlignment.CENTER);
        lblMsg.setMaxWidth(300);

        // Tamam Butonu
        Button btnOk = new Button("TAMAM");
        btnOk.setPrefWidth(120);
        btnOk.setStyle("-fx-background-color: " + btnColor + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;");

        btnOk.setOnAction(e -> stage.close());

        card.getChildren().addAll(lblTitle, lblMsg, btnOk);

        // Şeffaf Scene
        Scene scene = new Scene(card);
        scene.setFill(Color.TRANSPARENT); // Sahne şeffaf olsun ki sadece kart görünsün

        stage.setScene(scene);
        stage.showAndWait();
    }
}