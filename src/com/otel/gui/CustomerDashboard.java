package com.otel.gui;

import com.otel.db.DatabaseConnection;
import com.otel.model.BaseRoom;
import com.otel.pattern.HotelFacade;
import com.otel.pattern.RoomFactory;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality; // BU EKLENDİ (Hata 1 Çözümü)
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class CustomerDashboard {

    private String currentUserTc;
    private StackPane mainRoot;
    private BorderPane mainContentLayout;

    private TilePane roomGrid;
    private ScrollPane scrollPane;
    private BaseRoom selectedRoom;
    private VBox selectedCard;

    private DatePicker dpGiris;
    private DatePicker dpCikis;
    private TextField txtKisiSayisi;
    private ComboBox<String> cmbOdaTipi;

    private Label lblStatMusait, lblStatRezerve, lblStatDolu;

    public void show(String userName, String tcNo) {
        this.currentUserTc = tcNo;

        Stage stage = new Stage();
        stage.setTitle("TatilSepeti - Müşteri Paneli");

        String imageUrl = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=1920&auto=format&fit=crop";
        Image image = new Image(imageUrl);
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        mainRoot = new StackPane();
        mainRoot.setBackground(new Background(backgroundImage));

        mainContentLayout = new BorderPane();
        mainContentLayout.setPadding(new Insets(20));

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 25, 15, 25));
        header.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.95), new CornerRadii(15), Insets.EMPTY)));
        header.setEffect(new DropShadow(10, Color.rgb(0,0,0,0.1)));

        VBox titleBox = new VBox(2);
        Label lblBrand = new Label("KIRKLARELİ OTEL");
        lblBrand.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblBrand.setTextFill(Color.web("#2d3436")); // KOYU RENK (Okunabilirlik)

        Label lblUser = new Label("Hoşgeldin, " + userName);
        lblUser.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        lblUser.setTextFill(Color.web("#636e72")); // KOYU GRİ
        titleBox.getChildren().addAll(lblBrand, lblUser);

        Button btnRezervlerim = createWebButton("📅 Rezervasyonlarım", "#6c5ce7", "#a29bfe");
        Button btnProfil = createWebButton("👤 Profilim", "#0984e3", "#74b9ff");
        Button btnCikis = createWebButton("🚪 Çıkış", "#d63031", "#ff7675");
        btnCikis.setOnAction(e -> stage.close());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(titleBox, spacer, btnRezervlerim, btnProfil, btnCikis);

        mainContentLayout.setTop(header);

        VBox contentCard = new VBox(15);
        contentCard.setPadding(new Insets(20));
        contentCard.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.92), new CornerRadii(15), Insets.EMPTY)));
        contentCard.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.2)));
        BorderPane.setMargin(contentCard, new Insets(20, 0, 0, 0));

        lblStatMusait = createStatusLabel("MÜSAİT: 0", "#00b894");
        lblStatRezerve = createStatusLabel("REZERVE: 0", "#fdcb6e");
        lblStatDolu = createStatusLabel("DOLU: 0", "#d63031");
        HBox statusBox = new HBox(10, lblStatMusait, lblStatRezerve, lblStatDolu);
        updateStatusCounts();

        HBox searchBar = new HBox(15);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(15));
        searchBar.setStyle("-fx-background-color: #f1f2f6; -fx-background-radius: 12;");

        dpGiris = new DatePicker(LocalDate.now()); dpGiris.setPromptText("Giriş Tarihi"); dpGiris.setPrefWidth(130);
        dpCikis = new DatePicker(LocalDate.now().plusDays(1)); dpCikis.setPromptText("Çıkış Tarihi"); dpCikis.setPrefWidth(130);
        txtKisiSayisi = createStyledTextField("Kişi", "1", true); txtKisiSayisi.setPrefWidth(60);

        cmbOdaTipi = new ComboBox<>();
        cmbOdaTipi.getItems().addAll("HEPSI", "STANDART", "SUIT", "AILE", "EKONOMIK", "DELUXE", "KRAL");
        cmbOdaTipi.setValue("HEPSI");
        cmbOdaTipi.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-font-size: 14px; -fx-text-fill: black;"); // SİYAH YAZI

        Button btnAra = createWebButton("🔍 Odaları Listele", "#2d3436", "#636e72");

        // Etiketleri Koyu Renk Yapalım
        Label lblTarih = new Label("Tarih:"); lblTarih.setTextFill(Color.BLACK);
        Label lblKisi = new Label("Kişi:"); lblKisi.setTextFill(Color.BLACK);
        Label lblTip = new Label("Tip:"); lblTip.setTextFill(Color.BLACK);

        searchBar.getChildren().addAll(
                lblTarih, dpGiris, new Label("-"), dpCikis,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                lblKisi, txtKisiSayisi,
                lblTip, cmbOdaTipi,
                btnAra
        );

        roomGrid = new TilePane();
        roomGrid.setHgap(20); roomGrid.setVgap(20);
        roomGrid.setPrefColumns(4);
        roomGrid.setPadding(new Insets(10));
        roomGrid.setAlignment(Pos.TOP_LEFT);

        scrollPane = new ScrollPane(roomGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        searchRooms("HEPSI", 0);

        Button btnRezerv = new Button("SEÇİLİ ODAYI REZERVE ET ✅");
        btnRezerv.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        btnRezerv.setTextFill(Color.WHITE);
        btnRezerv.setPrefWidth(350);
        btnRezerv.setPrefHeight(55);
        btnRezerv.setStyle("-fx-background-color: #00b894; -fx-background-radius: 30; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        btnRezerv.setOnMouseEntered(e -> {
            btnRezerv.setStyle("-fx-background-color: #55efc4; -fx-background-radius: 30; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 15, 0, 0, 8);");
            btnRezerv.setScaleX(1.02); btnRezerv.setScaleY(1.02);
        });
        btnRezerv.setOnMouseExited(e -> {
            btnRezerv.setStyle("-fx-background-color: #00b894; -fx-background-radius: 30; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
            btnRezerv.setScaleX(1.0); btnRezerv.setScaleY(1.0);
        });

        HBox bottomBar = new HBox(btnRezerv);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(15));

        contentCard.getChildren().addAll(statusBox, searchBar, scrollPane, bottomBar);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        mainContentLayout.setCenter(contentCard);
        mainRoot.getChildren().add(mainContentLayout);

        // --- AKSİYONLAR ---
        btnAra.setOnAction(e -> {
            try {
                int k = txtKisiSayisi.getText().isEmpty() ? 0 : Integer.parseInt(txtKisiSayisi.getText());
                searchRooms(cmbOdaTipi.getValue(), k);
            } catch(Exception ex) { ModernAlert.show("Hata", "Kişi sayısı sayı olmalı.", Alert.AlertType.ERROR); }
        });

        btnRezervlerim.setOnAction(e -> new CustomerReservations().show(currentUserTc));
        btnProfil.setOnAction(e -> showModernProfileOverlay());

        btnRezerv.setOnAction(e -> {
            if (selectedRoom == null) { ModernAlert.show("Seçim Yok", "Lütfen bir oda seçiniz.", Alert.AlertType.WARNING); return; }
            if (!"MUSAIT".equals(selectedRoom.getStateName())) { ModernAlert.show("Uyarı", "Bu oda müsait değil.", Alert.AlertType.ERROR); return; }
            if (dpGiris.getValue() == null || dpCikis.getValue() == null) { ModernAlert.show("Eksik", "Tarihleri seçiniz.", Alert.AlertType.WARNING); return; }

            long gun = java.time.temporal.ChronoUnit.DAYS.between(dpGiris.getValue(), dpCikis.getValue());
            if (gun <= 0) { ModernAlert.show("Hata", "Tarihleri kontrol edin.", Alert.AlertType.ERROR); return; }

            double toplam = selectedRoom.getPrice() * gun;

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Onay");
            confirm.setHeaderText(gun + " Gece Konaklama - Toplam: " + toplam + " TL");
            confirm.setContentText("Oda: " + selectedRoom.getRoomNumber() + " (" + selectedRoom.getType() + ")\nİşlemi onaylıyor musunuz?");

            confirm.showAndWait().ifPresent(r -> {
                if (r == ButtonType.OK) {
                    if(new HotelFacade().placeReservation(selectedRoom, userName, currentUserTc, dpGiris.getValue(), dpCikis.getValue())) {
                        ModernAlert.show("Harika!", "Rezervasyonunuz alındı. İyi tatiller!", Alert.AlertType.INFORMATION);
                        searchRooms(cmbOdaTipi.getValue(), 0);
                        updateStatusCounts();
                        selectedRoom = null;
                    } else { ModernAlert.show("Hata", "İşlem başarısız.", Alert.AlertType.ERROR); }
                }
            });
        });

        Scene scene = new Scene(mainRoot, 1200, 800);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    // ========================================================================
    // MODERN PROFİL PENCERESİ (AVATARLI VE ŞIK)
    // ========================================================================
    // ========================================================================
    // YATAY PROFİL KARTI (GÜNCELLENDİ: TC ETİKETİ EKLENDİ)
    // ========================================================================
    private void showModernProfileOverlay() {
        StackPane overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        overlayPane.setAlignment(Pos.CENTER);

        HBox mainCard = new HBox(0);
        mainCard.setMaxSize(600, 320);
        mainCard.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 5);");

        String tempAd = "Müşteri";
        String tempMail = "";
        String tempTel = "";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT ad_soyad, email, telefon FROM users WHERE tc_no = ?");
            ps.setString(1, currentUserTc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tempAd = rs.getString("ad_soyad");
                tempMail = rs.getString("email");
                tempTel = rs.getString("telefon");
            }
        } catch (Exception e) {}

        // --- SOL TARAF (SIDEBAR) ---
        VBox leftSide = new VBox(15);
        leftSide.setPrefWidth(220);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setStyle("-fx-background-color: linear-gradient(to bottom, #6c5ce7, #a29bfe); -fx-background-radius: 15 0 0 15;");
        leftSide.setPadding(new Insets(20));

        StackPane avatar = new StackPane();
        javafx.scene.shape.Circle cBorder = new javafx.scene.shape.Circle(45, Color.WHITE);
        cBorder.setOpacity(0.3);
        javafx.scene.shape.Circle cInner = new javafx.scene.shape.Circle(40, Color.WHITE);
        Label lInit = new Label(tempAd.length() > 0 ? tempAd.substring(0, 1).toUpperCase() : "?");
        lInit.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        lInit.setTextFill(Color.web("#6c5ce7"));
        avatar.getChildren().addAll(cBorder, cInner, lInit);

        Label lblName = new Label(tempAd);
        lblName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblName.setTextFill(Color.WHITE);
        lblName.setWrapText(true);
        lblName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // --- BURAYI GÜNCELLEDİM: BAŞINA 'TC:' EKLENDİ ---
        Label lblTc = new Label("TC: " + currentUserTc);
        lblTc.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-padding: 3 10; -fx-background-radius: 10;");
        lblTc.setTextFill(Color.WHITE);
        lblTc.setFont(Font.font("Monospaced", FontWeight.BOLD, 13));
        // ------------------------------------------------

        leftSide.getChildren().addAll(avatar, lblName, lblTc);

        // --- SAĞ TARAF (FORM) ---
        VBox rightSide = new VBox(15);
        rightSide.setPadding(new Insets(20, 30, 20, 30));
        rightSide.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(rightSide, Priority.ALWAYS);

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);
        Label lblClose = new Label("✕");
        lblClose.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblClose.setTextFill(Color.GRAY);
        lblClose.setCursor(Cursor.HAND);
        lblClose.setOnMouseClicked(e -> mainRoot.getChildren().remove(overlayPane));

        Label lblHeader = new Label("Profil Bilgileri");
        lblHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        lblHeader.setTextFill(Color.web("#2d3436"));

        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(lblHeader, spacer, lblClose);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15); formGrid.setVgap(10);

        TextField txtEmail = createStyledTextField("E-posta", tempMail, true);
        TextField txtTel = createStyledTextField("Telefon", tempTel, true);

        formGrid.add(new Label("E-posta:"), 0, 0); formGrid.add(txtEmail, 1, 0);
        formGrid.add(new Label("Telefon:"), 0, 1); formGrid.add(txtTel, 1, 1);

        ColumnConstraints col1 = new ColumnConstraints(); col1.setMinWidth(60);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setHgrow(Priority.ALWAYS);
        formGrid.getColumnConstraints().addAll(col1, col2);

        CheckBox chkPass = new CheckBox("Şifremi güncellemek istiyorum");
        chkPass.setStyle("-fx-font-size: 12px; -fx-text-fill: #636e72;");

        VBox passBox = new VBox(8);
        passBox.setVisible(false); passBox.setManaged(false);
        passBox.setStyle("-fx-background-color: #f1f2f6; -fx-padding: 10; -fx-background-radius: 8;");

        PasswordField p1 = new PasswordField(); p1.setPromptText("Yeni Şifre"); p1.setStyle("-fx-background-radius: 5; -fx-border-color: #dfe6e9;");
        PasswordField p2 = new PasswordField(); p2.setPromptText("Yeni Şifre (Tekrar)"); p2.setStyle("-fx-background-radius: 5; -fx-border-color: #dfe6e9;");
        passBox.getChildren().addAll(p1, p2);

        chkPass.setOnAction(e -> {
            passBox.setVisible(chkPass.isSelected());
            passBox.setManaged(chkPass.isSelected());
        });

        Button btnSave = new Button("Kaydet");
        btnSave.setPrefWidth(120);
        btnSave.setStyle("-fx-background-color: #2d3436; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;");
        btnSave.setOnMouseEntered(e -> btnSave.setStyle("-fx-background-color: #00b894; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;"));
        btnSave.setOnMouseExited(e -> btnSave.setStyle("-fx-background-color: #2d3436; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;"));

        HBox btnContainer = new HBox(btnSave); btnContainer.setAlignment(Pos.CENTER_RIGHT);
        rightSide.getChildren().addAll(topBar, new Separator(), formGrid, chkPass, passBox, new Region(), btnContainer);
        VBox.setVgrow(btnContainer, Priority.ALWAYS);

        mainCard.getChildren().addAll(leftSide, rightSide);

        btnSave.setOnAction(e -> {
            try {
                Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement psUp = conn.prepareStatement("UPDATE users SET email=?, telefon=? WHERE tc_no=?");
                psUp.setString(1, txtEmail.getText());
                psUp.setString(2, txtTel.getText());
                psUp.setString(3, currentUserTc);
                psUp.executeUpdate();

                if (chkPass.isSelected()) {
                    if(!p1.getText().equals(p2.getText()) || p1.getText().isEmpty()) {
                        ModernAlert.show("Hata", "Şifreler uyuşmuyor!", Alert.AlertType.ERROR); return;
                    }
                    PreparedStatement psPass = conn.prepareStatement("UPDATE users SET sifre=? WHERE tc_no=?");
                    psPass.setString(1, p1.getText());
                    psPass.setString(2, currentUserTc);
                    psPass.executeUpdate();
                }
                ModernAlert.show("Başarılı", "Bilgiler güncellendi.", Alert.AlertType.INFORMATION);
                mainRoot.getChildren().remove(overlayPane);
            } catch(Exception ex) { ex.printStackTrace(); }
        });

        overlayPane.getChildren().add(mainCard);
        overlayPane.setOnMouseClicked(e -> { if(e.getTarget() == overlayPane) mainRoot.getChildren().remove(overlayPane); });
        mainRoot.getChildren().add(overlayPane);
    }

    // --- LAMBDA HATASI DÜZELTİLDİ: 'finalStyle' EKLENDİ ---
    private TextField createStyledTextField(String labelText, String value, boolean editable) {
        TextField tf = new TextField(value);
        tf.setPromptText(labelText);
        tf.setEditable(editable);

        String tempStyle = "-fx-font-size: 14px; -fx-padding: 12px; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #dfe6e9; -fx-border-width: 1px;";

        if (!editable) tempStyle += "-fx-background-color: #f1f2f6; -fx-text-fill: #636e72;"; // Okunabilir koyu gri
        else tempStyle += "-fx-background-color: white; -fx-text-fill: black;"; // Siyah yazı

        tf.setStyle(tempStyle);

        // KOPYA DEĞİŞKEN (Hata Çözümü)
        String finalStyle = tempStyle;

        if (editable) {
            tf.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) tf.setStyle(finalStyle + "-fx-border-color: #0984e3; -fx-effect: dropshadow(three-pass-box, rgba(9,132,227,0.2), 5, 0, 0, 0);");
                else tf.setStyle(finalStyle);
            });
        }
        return tf;
    }

    private Button createWebButton(String text, String colorHex, String hoverColorHex) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btn.setTextFill(Color.WHITE);
        String baseStyle = "-fx-background-color: " + colorHex + "; -fx-background-radius: 8; -fx-padding: 8 20 8 20; -fx-cursor: hand;";
        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-background-color: " + hoverColorHex + "; -fx-background-radius: 8; -fx-padding: 8 20 8 20; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
            ScaleTransition st = new ScaleTransition(Duration.millis(200), btn); st.setToX(1.05); st.setToY(1.05); st.play();
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle(baseStyle);
            ScaleTransition st = new ScaleTransition(Duration.millis(200), btn); st.setToX(1.0); st.setToY(1.0); st.play();
        });
        return btn;
    }

    // --- ÖZEL ODA KARTI TASARIMI (GÜNCELLENDİ: AÇIKLAMA EKLENDİ) ---
    // --- GÜNCELLENMİŞ ODA KARTI METODU (YAZI RENGİ DÜZELTİLDİ) ---
    private VBox createRoomCard(BaseRoom room) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefSize(200, 180);
        card.setAlignment(Pos.CENTER);

        // Tooltip (İpucu) Eklentisi
        Tooltip tooltip = new Tooltip(room.getDescription());
        tooltip.setStyle("-fx-font-size: 14px; -fx-background-color: #2d3436; -fx-text-fill: white;");
        Tooltip.install(card, tooltip);

        String status = room.getStateName();
        String color = "MUSAIT".equals(status) ? "#00b894" : ("DOLU".equals(status) ? "#d63031" : "#fdcb6e");
        String bgColor = "MUSAIT".equals(status) ? "white" : ("DOLU".equals(status) ? "#fab1a0" : "#ffeaa7");

        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label lblNo = new Label("Oda " + room.getRoomNumber());
        lblNo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        lblNo.setTextFill(Color.web("#2d3436"));

        Label lblTip = new Label(room.getType());
        lblTip.setStyle("-fx-background-color: #2d3436; -fx-text-fill: white; -fx-padding: 3 10; -fx-background-radius: 10; -fx-font-size: 11px;");

        Label lblFiyat = new Label(room.getPrice() + " ₺");
        lblFiyat.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblFiyat.setTextFill(Color.web("#0984e3"));

        // --- DÜZELTİLEN KISIM BURASI ---
        Label lblKap = new Label("👥 " + room.getCapacity() + " Kişilik");
        lblKap.setTextFill(Color.web("#2d3436")); // Koyu Gri (Siyaha yakın) yaparak okunurluğu artırdık
        lblKap.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        // -------------------------------

        Label lblDurum = new Label(status);
        lblDurum.setTextFill(Color.web(color));
        lblDurum.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        card.getChildren().addAll(lblNo, lblTip, new Separator(), lblFiyat, lblKap, lblDurum);

        // Seçim ve Efektler
        card.setOnMouseClicked(e -> {
            if (selectedCard != null) selectedCard.setStyle(selectedCard.getStyle().replace("-fx-border-color: #0984e3; -fx-border-width: 3;", ""));
            selectedRoom = room; selectedCard = card;
            card.setStyle(card.getStyle() + "-fx-border-color: #0984e3; -fx-border-width: 3; -fx-border-radius: 15;");
        });

        card.setOnMouseEntered(e -> {
            card.setScaleX(1.05); card.setScaleY(1.05);
            card.setEffect(new DropShadow(15, Color.rgb(0,0,0,0.2)));
        });
        card.setOnMouseExited(e -> {
            card.setScaleX(1.0); card.setScaleY(1.0);
            card.setEffect(new DropShadow(5, Color.rgb(0,0,0,0.1)));
        });

        return card;
    }

    private void searchRooms(String tip, int kap) {
        roomGrid.getChildren().clear(); selectedRoom = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM rooms WHERE 1=1");
        if (kap > 0) sql.append(" AND kapasite >= ").append(kap);
        if (!"HEPSI".equals(tip)) sql.append(" AND tip = '").append(tip).append("'");
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sql.toString());
            while (rs.next()) {
                roomGrid.getChildren().add(createRoomCard(RoomFactory.createRoom(
                        rs.getString("tip"), rs.getInt("oda_no"), rs.getDouble("fiyat"),
                        rs.getInt("kapasite"), rs.getString("durum")
                )));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void updateStatusCounts() {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT durum, COUNT(*) as s FROM rooms GROUP BY durum");
            int m=0, r=0, d=0;
            while(rs.next()) {
                String s = rs.getString("durum");
                int c = rs.getInt("s");
                if("MUSAIT".equals(s)) m=c; else if("REZERVE".equals(s)) r=c; else d=c;
            }
            lblStatMusait.setText("MÜSAİT: " + m); lblStatRezerve.setText("REZERVE: " + r); lblStatDolu.setText("DOLU: " + d);
        } catch(Exception e){}
    }

    private Label createStatusLabel(String t, String c) {
        Label l = new Label(t);
        l.setStyle("-fx-background-color: "+c+"; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 15; -fx-font-weight: bold; -fx-font-size: 12px;");
        return l;
    }
}