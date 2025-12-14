package com.otel.gui;

import com.otel.db.DatabaseConnection;
import com.otel.pattern.HotelFacade;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;

public class CustomerReservations {

    private String currentUserTc;

    // Kartları dizeceğimiz alanlar (Tablo yerine bunlar var)
    private TilePane activePane;
    private TilePane historyPane;

    public void show(String tcNo) {
        this.currentUserTc = tcNo;
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Rezervasyonlarım");

        // --- 1. ARKA PLAN ---
        String imageUrl = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=1920&auto=format&fit=crop";
        Image image = new Image(imageUrl);
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, true);
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));

        // --- 2. ANA PANEL ---
        VBox mainCard = new VBox(15);
        mainCard.setPadding(new Insets(20));
        mainCard.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.95), new CornerRadii(15), Insets.EMPTY)));
        mainCard.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));
        StackPane.setMargin(mainCard, new Insets(30));

        // Başlık
        Label lblHeader = new Label("Rezervasyonlarım ve Geçmişim");
        lblHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblHeader.setTextFill(Color.DARKSLATEGRAY);

        // --- 3. SEKMELER (TABS) ---
        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: transparent;");

        // Panelleri Hazırla (Grid Yapısı)
        activePane = createGridPane();
        historyPane = createGridPane();

        // ScrollPane içine koy (Kaydırma özelliği için)
        ScrollPane scrollActive = createScrollPane(activePane);
        ScrollPane scrollHistory = createScrollPane(historyPane);

        Tab tabActive = new Tab("Aktif & Gelecek", scrollActive);
        tabActive.setClosable(false);

        Tab tabHistory = new Tab("Geçmiş Konaklamalar", scrollHistory);
        tabHistory.setClosable(false);

        tabPane.getTabs().addAll(tabActive, tabHistory);

        // Verileri Yükle
        loadData();

        mainCard.getChildren().addAll(lblHeader, new Separator(), tabPane);
        root.getChildren().add(mainCard);

        Scene scene = new Scene(root, 1000, 650);
        stage.setScene(scene);
        stage.show();
    }

    // --- YARDIMCI: GRID VE SCROLL OLUŞTURUCU ---
    private TilePane createGridPane() {
        TilePane pane = new TilePane();
        pane.setHgap(20); pane.setVgap(20);
        pane.setPadding(new Insets(15));
        pane.setPrefColumns(3); // Yan yana 3 kart
        pane.setAlignment(Pos.TOP_LEFT);
        return pane;
    }

    private ScrollPane createScrollPane(TilePane content) {
        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return sp;
    }

    // --- VERİLERİ YÜKLE VE KARTLARA DÖNÜŞTÜR ---
    private void loadData() {
        activePane.getChildren().clear();
        historyPane.getChildren().clear();

        LocalDate bugun = LocalDate.now();

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM reservations WHERE tc_no = ? ORDER BY giris_tarihi DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, currentUserTc);
            ResultSet rs = ps.executeQuery();

            boolean hasActive = false;
            boolean hasHistory = false;

            while (rs.next()) {
                String durum = rs.getString("durum");
                String odemeDurumu = rs.getString("odeme_durumu");
                if (odemeDurumu == null) odemeDurumu = "BEKLIYOR";
                Date cikis = rs.getDate("cikis_tarihi");

                ReservationModel model = new ReservationModel(
                        rs.getInt("id"), rs.getInt("oda_no"), rs.getDate("giris_tarihi"),
                        cikis, rs.getDouble("ucret"), durum, odemeDurumu
                );

                // Kart Oluştur
                VBox card = createReservationCard(model);

                // Ayrıştırma
                if (durum.equals("IPTAL") || cikis.toLocalDate().isBefore(bugun)) {
                    historyPane.getChildren().add(card); // Geçmişe ekle
                    hasHistory = true;
                } else {
                    activePane.getChildren().add(card); // Aktife ekle
                    hasActive = true;
                }
            }

            // Eğer liste boşsa kullanıcıya bilgi verelim
            if (!hasActive) activePane.getChildren().add(createEmptyMessage("Aktif rezervasyonunuz bulunmuyor."));
            if (!hasHistory) historyPane.getChildren().add(createEmptyMessage("Geçmiş kaydınız bulunmuyor."));

        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- KART TASARIMI (CORE PART) ---
    private VBox createReservationCard(ReservationModel r) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefWidth(280);

        // Stil: Beyaz kart, yuvarlak köşe, gölge
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        // 1. Üst Kısım: Oda No ve Tarih
        Label lblOda = new Label("ODA " + r.getOdaNo());
        lblOda.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblOda.setTextFill(Color.web("#2d3436"));

        Label lblTarih = new Label("📅 " + r.getGiris() + "  ⮕  " + r.getCikis());
        lblTarih.setTextFill(Color.GRAY);
        lblTarih.setFont(Font.font("Segoe UI", 12));

        // 2. Orta Kısım: Ücret ve Durum
        Label lblFiyat = new Label(r.getUcret() + " TL");
        lblFiyat.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblFiyat.setTextFill(Color.web("#0984e3"));

        // Durum Etiketi (Renkli)
        String durumText = r.getDurum();
        String durumColor = "#00b894"; // Yeşil (Aktif)
        if("IPTAL".equals(durumText)) { durumColor = "#d63031"; durumText = "İPTAL EDİLDİ"; }
        else if(r.getCikis().toLocalDate().isBefore(LocalDate.now())) { durumColor = "#636e72"; durumText = "TAMAMLANDI"; }

        Label lblDurum = new Label(durumText);
        lblDurum.setStyle("-fx-background-color: "+durumColor+"; -fx-text-fill: white; -fx-padding: 3 8; -fx-background-radius: 5; -fx-font-size: 10px;");

        // Ödeme Durumu
        boolean isPaid = "ODENDI".equals(r.getOdemeDurumu());
        Label lblOdeme = new Label(isPaid ? "✅ Ödeme Alındı" : "⚠️ Ödeme Bekleniyor");
        lblOdeme.setTextFill(isPaid ? Color.GREEN : Color.ORANGE);
        lblOdeme.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        // 3. Alt Kısım: Butonlar (Sadece Aktif ve İptal Edilmemişse)
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        // Eğer hala aktifse butonları göster
        if (!"IPTAL".equals(r.getDurum()) && !r.getCikis().toLocalDate().isBefore(LocalDate.now())) {

            // ÖDEME BUTONU (Sadece ödenmemişse çıkar)
            if (!isPaid) {
                Button btnOde = new Button("💳 ÖDE");
                btnOde.setStyle("-fx-background-color: #f1c40f; -fx-text-fill: black; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");
                btnOde.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(btnOde, Priority.ALWAYS);
                btnOde.setOnAction(e -> processPayment(r));
                buttonBox.getChildren().add(btnOde);
            }

            // İPTAL BUTONU
            Button btnIptal = new Button("İPTAL ET");
            btnIptal.setStyle("-fx-background-color: #ff7675; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 5;");
            btnIptal.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(btnIptal, Priority.ALWAYS);
            btnIptal.setOnAction(e -> cancelReservation(r));

            buttonBox.getChildren().add(btnIptal);
        }

        // Kartı Birleştir
        HBox topRow = new HBox(lblOda, new Region(), lblDurum);
        HBox.setHgrow(topRow.getChildren().get(1), Priority.ALWAYS);

        card.getChildren().addAll(topRow, lblTarih, new Separator(), lblFiyat, lblOdeme, new Region(), buttonBox);
        VBox.setVgrow(card.getChildren().get(5), Priority.ALWAYS); // Spacer for layout

        return card;
    }

    private Label createEmptyMessage(String msg) {
        Label l = new Label(msg);
        l.setFont(Font.font("Segoe UI", 16));
        l.setTextFill(Color.GRAY);
        l.setPadding(new Insets(20));
        return l;
    }

    // --- ÖDEME İŞLEMİ (Pop-up) ---
    private void processPayment(ReservationModel model) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Güvenli Ödeme");
        dialog.setHeaderText(model.getUcret() + " TL Ödeme İşlemi");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20, 50, 10, 10));

        TextField txtKart = new TextField(); txtKart.setPromptText("Kart No");
        TextField txtSkt = new TextField(); txtSkt.setPromptText("AA/YY");
        TextField txtCvv = new TextField(); txtCvv.setPromptText("CVV");

        grid.add(new Label("Kart No:"), 0, 0); grid.add(txtKart, 1, 0);
        grid.add(new Label("SKT:"), 0, 1); grid.add(txtSkt, 1, 1);
        grid.add(new Label("CVV:"), 0, 2); grid.add(txtCvv, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                if (txtKart.getText().length() < 16 || txtCvv.getText().isEmpty()) {
                    ModernAlert.show("Hata", "Kart bilgileri geçersiz.", Alert.AlertType.ERROR);
                } else {
                    if (new HotelFacade().confirmPayment(model.getId())) {
                        ModernAlert.show("Başarılı", "Ödeme alındı.", Alert.AlertType.INFORMATION);
                        loadData(); // Yenile
                    }
                }
            }
        });
    }

    // --- İPTAL İŞLEMİ (Kesintili) ---
    private void cancelReservation(ReservationModel model) {
        boolean isPaid = "ODENDI".equals(model.getOdemeDurumu());
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("İptal");

        if (isPaid) {
            double iade = model.getUcret() * 0.8; // %20 kesinti
            confirm.setHeaderText("⚠️ Ücret İadesi");
            confirm.setContentText("Ödeme yapıldığı için %20 kesinti uygulanacak.\nİade Edilecek: " + iade + " TL\nOnaylıyor musunuz?");
        } else {
            confirm.setHeaderText("Rezervasyon İptali");
            confirm.setContentText("Rezervasyonu iptal etmek istediğinize emin misiniz?");
        }

        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                if (new HotelFacade().cancelReservation(model.getId(), model.getOdaNo())) {
                    ModernAlert.show("Başarılı", "İptal edildi.", Alert.AlertType.INFORMATION);
                    loadData();
                }
            }
        });
    }

    // --- MODEL SINIFI ---
    public static class ReservationModel {
        private int id; private int odaNo; private Date giris; private Date cikis; private double ucret; private String durum; private String odemeDurumu;
        public ReservationModel(int id, int odaNo, Date giris, Date cikis, double ucret, String durum, String odemeDurumu) {
            this.id = id; this.odaNo = odaNo; this.giris = giris; this.cikis = cikis; this.ucret = ucret; this.durum = durum; this.odemeDurumu = odemeDurumu;
        }
        public int getId() { return id; }
        public int getOdaNo() { return odaNo; }
        public Date getGiris() { return giris; }
        public Date getCikis() { return cikis; }
        public double getUcret() { return ucret; }
        public String getDurum() { return durum; }
        public String getOdemeDurumu() { return odemeDurumu; }
    }
}