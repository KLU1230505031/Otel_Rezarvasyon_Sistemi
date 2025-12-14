package com.otel.gui;

import com.otel.db.DatabaseConnection;
import com.otel.model.BaseRoom;
import com.otel.model.Customer;
import com.otel.model.Reservation;
import com.otel.pattern.RoomFactory;
import com.otel.pattern.HotelFacade; // BU EKLENDİ (Hata Çözümü)
import javafx.animation.ScaleTransition; // BU EKLENDİ (Hata Çözümü)
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration; // BU EKLENDİ (Hata Çözümü)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class PersonnelDashboard {

    private BorderPane mainLayout;
    private StackPane rootPane;

    private TilePane roomGrid;
    private VBox customerListContainer;
    private VBox reservationListContainer;

    public void show(String userName) {
        Stage stage = new Stage();
        stage.setTitle("Yönetim Paneli - " + userName);

        String imageUrl = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=1920&auto=format&fit=crop";
        Image image = new Image(imageUrl);
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        rootPane = new StackPane();
        rootPane.setBackground(new Background(backgroundImage));

        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));
        rootPane.getChildren().add(mainLayout);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 30, 15, 30));
        header.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.95), new CornerRadii(15), Insets.EMPTY)));
        header.setEffect(new DropShadow(10, Color.rgb(0,0,0,0.1)));

        VBox brandBox = new VBox(2);
        Label lblBrand = new Label("OTEL YÖNETİMİ");
        lblBrand.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        lblBrand.setTextFill(Color.web("#2d3436"));
        Label lblUser = new Label("Personel: " + userName);
        lblUser.setFont(Font.font("Segoe UI", 12));
        lblUser.setTextFill(Color.web("#636e72"));
        brandBox.getChildren().addAll(lblBrand, lblUser);

        Button btnOdalar = createWebButton("🛏️ Oda Yönetimi", "#6c5ce7", "#a29bfe");
        Button btnMusteriler = createWebButton("👥 Müşteriler", "#0984e3", "#74b9ff");
        Button btnRezervasyonlar = createWebButton("📅 Rezervasyonlar", "#00b894", "#55efc4");
        Button btnCikis = createWebButton("🚪 Çıkış", "#d63031", "#ff7675");

        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(brandBox, spacer, btnOdalar, btnMusteriler, btnRezervasyonlar, btnCikis);

        mainLayout.setTop(header);
        BorderPane.setMargin(header, new Insets(0, 0, 20, 0));

        switchView("ROOMS");

        btnOdalar.setOnAction(e -> switchView("ROOMS"));
        btnMusteriler.setOnAction(e -> switchView("CUSTOMERS"));
        btnRezervasyonlar.setOnAction(e -> switchView("RESERVATIONS"));
        btnCikis.setOnAction(e -> stage.close());

        Scene scene = new Scene(rootPane, 1280, 800);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private void switchView(String viewName) {
        VBox content = null;
        if ("ROOMS".equals(viewName)) content = createRoomView();
        else if ("CUSTOMERS".equals(viewName)) content = createCustomerView();
        else if ("RESERVATIONS".equals(viewName)) content = createReservationView();

        if (content != null) {
            content.setPadding(new Insets(20));
            content.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.92), new CornerRadii(15), Insets.EMPTY)));
            content.setEffect(new DropShadow(20, Color.rgb(0,0,0,0.2)));
            mainLayout.setCenter(content);
        }
    }

    private VBox createRoomView() {
        HBox toolBar = new HBox(15);
        toolBar.setAlignment(Pos.CENTER_LEFT);
        toolBar.setPadding(new Insets(10));
        toolBar.setStyle("-fx-background-color: #f1f2f6; -fx-background-radius: 10;");

        TextField txtNo = createStyledTextField("No", 60);
        ComboBox<String> cmbTip = new ComboBox<>();
        cmbTip.getItems().addAll("STANDART", "SUIT", "AILE", "EKONOMIK", "DELUXE", "KRAL");
        cmbTip.setValue("STANDART"); cmbTip.setStyle("-fx-background-color: white; -fx-font-size: 14px; -fx-text-fill: black;");
        TextField txtFiyat = createStyledTextField("Fiyat", 80);
        TextField txtKap = createStyledTextField("Kişi", 60);
        Button btnEkle = createWebButton("➕ Oda Ekle", "#0984e3", "#74b9ff");

        btnEkle.setOnAction(e -> {
            try {
                addRoomToDB(Integer.parseInt(txtNo.getText()), cmbTip.getValue(), Double.parseDouble(txtFiyat.getText()), Integer.parseInt(txtKap.getText()));
                loadRooms();
            } catch(Exception ex) { showAlert("Hata", "Bilgileri kontrol ediniz."); }
        });

        // Etiket Renkleri (Siyah)
        Label l1 = new Label("Yeni Oda:"); l1.setTextFill(Color.BLACK);
        toolBar.getChildren().addAll(l1, txtNo, cmbTip, txtFiyat, txtKap, btnEkle);

        roomGrid = new TilePane();
        roomGrid.setHgap(20); roomGrid.setVgap(20);
        roomGrid.setPrefColumns(5);
        roomGrid.setAlignment(Pos.TOP_LEFT);

        ScrollPane scroll = new ScrollPane(roomGrid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        loadRooms();

        VBox layout = new VBox(15, createHeader("Oda Yönetimi"), toolBar, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return layout;
    }

    private void loadRooms() {
        roomGrid.getChildren().clear();
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM rooms ORDER BY oda_no");
            while (rs.next()) {
                BaseRoom room = RoomFactory.createRoom(rs.getString("tip"), rs.getInt("oda_no"), rs.getDouble("fiyat"), rs.getInt("kapasite"), rs.getString("durum"));
                roomGrid.getChildren().add(createPersonnelRoomCard(room));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private VBox createPersonnelRoomCard(BaseRoom room) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(10));
        card.setPrefSize(180, 210);
        card.setAlignment(Pos.CENTER);

        String status = room.getStateName();
        String bgColor = "MUSAIT".equals(status) ? "white" : ("DOLU".equals(status) ? "#fab1a0" : "#ffeaa7");
        String borderColor = "MUSAIT".equals(status) ? "#00b894" : ("DOLU".equals(status) ? "#d63031" : "#fdcb6e");

        card.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 10; -fx-border-color: " + borderColor + "; -fx-border-width: 2; -fx-border-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        Label lblNo = new Label("Oda " + room.getRoomNumber());
        lblNo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblNo.setTextFill(Color.web("#2d3436")); // KOYU RENK

        Label lblInfo = new Label(room.getType() + " (" + room.getCapacity() + " Kişi)");
        lblInfo.setStyle("-fx-font-size: 11px; -fx-text-fill: #636e72;");

        Label lblDurum = new Label(status);
        lblDurum.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblDurum.setTextFill(Color.web(borderColor));

        VBox actions = new VBox(5); actions.setAlignment(Pos.CENTER);

        if ("MUSAIT".equals(status)) {
            Button btnRezerv = createSmallButton("Müşteri İçin Rezerve Et", "#6c5ce7");
            btnRezerv.setOnAction(e -> showPersonnelReservationPopup(room));
            Button btnSil = createSmallButton("Sil", "#d63031");
            btnSil.setOnAction(e -> { deleteRoomFromDB(room.getRoomNumber()); loadRooms(); });
            actions.getChildren().addAll(btnRezerv, btnSil);
        } else if ("REZERVE".equals(status)) {
            Button btnCheckIn = createSmallButton("Giriş Onayla (Check-In)", "#e67e22");
            btnCheckIn.setOnAction(e -> { new HotelFacade().approveCheckIn(room); loadRooms(); });
            actions.getChildren().add(btnCheckIn);
        } else if ("DOLU".equals(status)) {
            Button btnCheckOut = createSmallButton("Çıkış Yap (Check-Out)", "#00b894");
            btnCheckOut.setOnAction(e -> { changeRoomStatus(room.getRoomNumber(), "MUSAIT"); });
            actions.getChildren().add(btnCheckOut);
        }

        card.getChildren().addAll(lblNo, lblInfo, new Separator(), lblDurum, new Region(), actions);
        return card;
    }

    private VBox createCustomerView() {
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);

        TextField txtAd = createStyledTextField("Ad Soyad Ara...", 150);
        TextField txtTc = createStyledTextField("TC No", 120); addTextLimiter(txtTc, 11);
        TextField txtTel = createStyledTextField("Telefon", 120); addTextLimiter(txtTel, 11);

        Button btnAra = createWebButton("🔍 Filtrele", "#6c5ce7", "#a29bfe");
        Button btnTemizle = createWebButton("X", "#b2bec3", "#dfe6e9");
        Button btnYeni = createWebButton("➕ Yeni Müşteri", "#e17055", "#fab1a0");

        btnAra.setOnAction(e -> loadCustomers(txtAd.getText(), txtTc.getText(), txtTel.getText()));

        // DÜZELTME: 3 Parametre ile çağırılıyor
        btnTemizle.setOnAction(e -> {
            txtAd.clear(); txtTc.clear(); txtTel.clear();
            loadCustomers(null, null, null);
        });

        btnYeni.setOnAction(e -> showCustomerRegisterOverlay());

        Label lblFiltre = new Label("Filtrele:"); lblFiltre.setTextFill(Color.BLACK);
        searchBar.getChildren().addAll(lblFiltre, txtAd, txtTc, txtTel, btnAra, btnTemizle, new Region(), btnYeni);
        HBox.setHgrow(searchBar.getChildren().get(6), Priority.ALWAYS);

        customerListContainer = new VBox(10);
        customerListContainer.setPadding(new Insets(10));

        ScrollPane scroll = new ScrollPane(customerListContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        loadCustomers(null, null, null);

        VBox layout = new VBox(15, createHeader("Müşteri Listesi (Rehber)"), searchBar, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return layout;
    }

    // DÜZELTME: 3 Parametre (Ad, TC, Tel)
    private void loadCustomers(String a, String t, String p) {
        customerListContainer.getChildren().clear();
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE rol='MUSTERI'");
        if(a!=null && !a.isEmpty()) sql.append(" AND ad_soyad LIKE '%"+a+"%'");
        if(t!=null && !t.isEmpty()) sql.append(" AND tc_no LIKE '%"+t+"%'");
        if(p!=null && !p.isEmpty()) sql.append(" AND telefon LIKE '%"+p+"%'");
        try {
            ResultSet rs = DatabaseConnection.getInstance().getConnection().createStatement().executeQuery(sql.toString());
            while(rs.next()) {
                Customer c = new Customer(rs.getInt("id"), rs.getString("ad_soyad"), rs.getString("tc_no"), rs.getString("email"), rs.getString("telefon"));
                customerListContainer.getChildren().add(createCustomerListItem(c));
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    private HBox createCustomerListItem(Customer c) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 20, 10, 20));
        row.setStyle("-fx-background-color: white; -fx-background-radius: 50; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        StackPane avatar = new StackPane();
        Circle circle = new Circle(22, Color.web("#74b9ff"));
        Label initial = new Label(c.getAdSoyad().substring(0, 1).toUpperCase());
        initial.setTextFill(Color.WHITE); initial.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        avatar.getChildren().addAll(circle, initial);

        VBox info = new VBox(2);
        Label name = new Label(c.getAdSoyad());
        name.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        name.setTextFill(Color.web("#2d3436")); // KOYU YAZI

        Label tc = new Label("TC: " + c.getTcNo() + "  |  Tel: " + c.getTelefon());
        tc.setTextFill(Color.web("#636e72")); tc.setFont(Font.font(12)); // KOYU GRİ
        info.getChildren().addAll(name, tc);

        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnDetail = createSmallButton("📋 Geçmiş", "#2d3436");
        btnDetail.setOnAction(e -> new CustomerReservations().show(c.getTcNo()));

        row.getChildren().addAll(avatar, info, spacer, btnDetail);

        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #f1f2f6; -fx-background-radius: 50; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: white; -fx-background-radius: 50; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);"));

        return row;
    }

    private VBox createReservationView() {
        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER_LEFT);

        TextField txtMus = createStyledTextField("Müşteri Adı", 130);
        TextField txtOda = createStyledTextField("Oda No", 70);
        DatePicker dp1 = new DatePicker(); dp1.setPromptText("Başlangıç"); dp1.setPrefWidth(110);
        DatePicker dp2 = new DatePicker(); dp2.setPromptText("Bitiş"); dp2.setPrefWidth(110);
        ComboBox<String> cmbTip = new ComboBox<>();
        cmbTip.getItems().addAll("HEPSI", "STANDART", "SUIT", "AILE", "EKONOMIK", "DELUXE", "KRAL");
        cmbTip.setValue("HEPSI"); cmbTip.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        Button btnFiltre = createWebButton("🔍", "#0984e3", "#74b9ff");
        Button btnTemiz = createWebButton("X", "#b2bec3", "#dfe6e9");

        btnFiltre.setOnAction(e -> loadAllReservations(dp1.getValue(), dp2.getValue(), txtOda.getText(), txtMus.getText(), cmbTip.getValue()));
        btnTemiz.setOnAction(e -> { txtMus.clear(); txtOda.clear(); dp1.setValue(null); dp2.setValue(null); cmbTip.setValue("HEPSI"); loadAllReservations(null,null,null,null,"HEPSI"); });

        filters.getChildren().addAll(txtMus, txtOda, cmbTip, dp1, dp2, btnFiltre, btnTemiz);

        reservationListContainer = new VBox(12);
        reservationListContainer.setPadding(new Insets(10));

        ScrollPane scroll = new ScrollPane(reservationListContainer);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        loadAllReservations(null, null, null, null, "HEPSI");

        VBox layout = new VBox(15, createHeader("Rezervasyon İşlemleri"), filters, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return layout;
    }

    private void loadAllReservations(LocalDate d1, LocalDate d2, String rNo, String name, String type) {
        reservationListContainer.getChildren().clear();
        StringBuilder sql = new StringBuilder("SELECT r.*, u.ad_soyad, rm.tip FROM reservations r JOIN users u ON r.tc_no=u.tc_no JOIN rooms rm ON r.oda_no=rm.oda_no WHERE 1=1");
        if(rNo!=null && !rNo.isEmpty()) sql.append(" AND r.oda_no="+rNo);
        if(name!=null && !name.isEmpty()) sql.append(" AND u.ad_soyad LIKE '%"+name+"%'");
        if(!"HEPSI".equals(type) && type!=null) sql.append(" AND rm.tip='"+type+"'");

        try { ResultSet rs = DatabaseConnection.getInstance().getConnection().createStatement().executeQuery(sql.toString());
            while(rs.next()) {
                Reservation r = new Reservation(rs.getInt("id"), rs.getInt("oda_no"), rs.getDate("giris_tarihi"), rs.getDate("cikis_tarihi"), rs.getDouble("ucret"), rs.getString("durum"));
                r.setCustomerName(rs.getString("ad_soyad")); r.setPaymentStatus(rs.getString("odeme_durumu"));
                reservationListContainer.getChildren().add(createReservationListItem(r));
            } } catch(Exception e){}
    }

    private HBox createReservationListItem(Reservation r) {
        HBox row = new HBox(0);
        row.setAlignment(Pos.CENTER_LEFT);

        String statusColor = "IPTAL".equals(r.getStatus()) ? "#d63031" : ("ODENDI".equals(r.getPaymentStatus()) ? "#00b894" : "#fdcb6e");
        Region statusStrip = new Region();
        statusStrip.setPrefSize(6, 70);
        statusStrip.setStyle("-fx-background-color: " + statusColor + "; -fx-background-radius: 5 0 0 5;");

        HBox content = new HBox(20);
        content.setPadding(new Insets(10, 20, 10, 15));
        content.setAlignment(Pos.CENTER_LEFT);
        content.setStyle("-fx-background-color: white; -fx-background-radius: 0 5 5 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 5, 0, 0, 2);");
        HBox.setHgrow(content, Priority.ALWAYS);

        VBox boxOda = new VBox(2, new Label("ODA"), new Label(String.valueOf(r.getRoomNumber())));
        ((Label)boxOda.getChildren().get(0)).setStyle("-fx-font-size:10px; -fx-text-fill:gray;");
        ((Label)boxOda.getChildren().get(1)).setFont(Font.font("Arial", FontWeight.BOLD, 20));
        ((Label)boxOda.getChildren().get(1)).setTextFill(Color.BLACK); // SİYAH

        VBox boxUser = new VBox(2, new Label("MÜŞTERİ"), new Label(r.getCustomerName()));
        ((Label)boxUser.getChildren().get(0)).setStyle("-fx-font-size:10px; -fx-text-fill:gray;");
        ((Label)boxUser.getChildren().get(1)).setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        ((Label)boxUser.getChildren().get(1)).setTextFill(Color.BLACK); // SİYAH
        boxUser.setPrefWidth(150);

        VBox boxDate = new VBox(2, new Label("TARİH ARALIĞI"), new Label(r.getCheckIn() + " ⮕ " + r.getCheckOut()));
        ((Label)boxDate.getChildren().get(0)).setStyle("-fx-font-size:10px; -fx-text-fill:gray;");
        ((Label)boxDate.getChildren().get(1)).setTextFill(Color.DARKGRAY);

        VBox boxPrice = new VBox(2, new Label(r.getPrice() + " TL"), new Label(r.getPaymentStatus()));
        boxPrice.setPrefWidth(100);             // Genişliği sabitle (100px)
        boxPrice.setAlignment(Pos.CENTER_RIGHT);
        ((Label)boxPrice.getChildren().get(0)).setFont(Font.font("Arial", FontWeight.BOLD, 16));
        ((Label)boxPrice.getChildren().get(0)).setTextFill(Color.web("#0984e3"));
        String payColor = "ODENDI".equals(r.getPaymentStatus()) ? "green" : "orange";
        ((Label)boxPrice.getChildren().get(1)).setStyle("-fx-text-fill: " + payColor + "; -fx-font-weight: bold; -fx-font-size: 11px;");

        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnAction = null;
        if (!"ODENDI".equals(r.getPaymentStatus()) && "AKTIF".equals(r.getStatus())) {
            btnAction = createSmallButton("Tahsil Et", "#27ae60");
            btnAction.setOnAction(e -> {
                if(new HotelFacade().confirmPayment(r.getId())) { showAlert("Başarılı", "Tahsilat Onaylandı"); loadAllReservations(null,null,null,null,"HEPSI"); }
            });
        } else {
            btnAction = new Button(r.getStatus());
            btnAction.setDisable(true);
            btnAction.setStyle("-fx-background-color: transparent; -fx-text-fill: gray; -fx-border-color: #dfe6e9; -fx-border-radius: 5;");
        }
        if (btnAction != null) {
            btnAction.setPrefWidth(140); // Buton genişliğini sabitle (Örn: 140px)
            btnAction.setAlignment(Pos.CENTER); // Yazıyı ortala
        }

        content.getChildren().addAll(boxOda, new Separator(javafx.geometry.Orientation.VERTICAL), boxUser, boxDate, spacer, boxPrice, btnAction);

        row.getChildren().addAll(statusStrip, content);
        HBox.setHgrow(row, Priority.ALWAYS);
        return row;
    }

    private void showCustomerRegisterOverlay() {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.5);");
        overlay.setAlignment(Pos.CENTER);

        VBox form = new VBox(15);
        form.setMaxWidth(350);
        form.setPadding(new Insets(30));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        Label title = new Label("Yeni Müşteri Kaydı"); title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        title.setTextFill(Color.BLACK);

        TextField tAd = createStyledTextField("Ad Soyad", 300);
        TextField tTc = createStyledTextField("TC No", 300);
        TextField tMail = createStyledTextField("E-posta", 300);
        TextField tTel = createStyledTextField("Telefon", 300);
        TextField tPass = createStyledTextField("Şifre", 300);

        HBox buttons = new HBox(10);
        Button btnSave = createWebButton("Kaydet", "#00b894", "#55efc4");
        Button btnCancel = createWebButton("İptal", "#d63031", "#ff7675");

        btnCancel.setOnAction(e -> rootPane.getChildren().remove(overlay));

        btnSave.setOnAction(e -> {
            try {
                Connection c = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = c.prepareStatement("INSERT INTO users (tc_no, ad_soyad, email, telefon, sifre, rol) VALUES (?, ?, ?, ?, ?, 'MUSTERI')");
                ps.setString(1, tTc.getText()); ps.setString(2, tAd.getText()); ps.setString(3, tMail.getText()); ps.setString(4, tTel.getText()); ps.setString(5, tPass.getText());
                ps.executeUpdate();
                showAlert("Başarılı", "Müşteri kaydedildi");
                loadCustomers(null, null, null); // DÜZELTİLDİ: 3 argüman
                rootPane.getChildren().remove(overlay);
            } catch(Exception ex) { showAlert("Hata", "Kayıt yapılamadı."); }
        });

        buttons.getChildren().addAll(btnSave, btnCancel);
        form.getChildren().addAll(title, new Separator(), tAd, tTc, tMail, tTel, tPass, buttons);

        overlay.getChildren().add(form);
        rootPane.getChildren().add(overlay);
    }

    private void showPersonnelReservationPopup(BaseRoom room) {
        Stage s = new Stage(); s.setTitle("Müşteri Adına Rezervasyon");
        VBox l = new VBox(15); l.setPadding(new Insets(20)); l.setStyle("-fx-background-color: white;");
        Label lblInfo = new Label("Oda: " + room.getRoomNumber() + " (" + room.getType() + ")");
        lblInfo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblInfo.setTextFill(Color.BLACK);
        TextField tTc = createStyledTextField("Müşteri TC", 200);
        DatePicker dIn = new DatePicker(LocalDate.now()); dIn.setPromptText("Giriş");
        DatePicker dOut = new DatePicker(LocalDate.now().plusDays(1)); dOut.setPromptText("Çıkış");
        Button btn = createWebButton("Oluştur", "#27ae60", "#2ecc71");
        btn.setOnAction(e -> {
            String name = fetchUserNameByTC(tTc.getText());
            if(name == null) { showAlert("Hata", "Müşteri bulunamadı."); return; }
            if(new HotelFacade().placeReservation(room, name, tTc.getText(), dIn.getValue(), dOut.getValue())) {
                showAlert("Başarılı", "Rezervasyon yapıldı."); loadRooms(); s.close();
            } else showAlert("Hata", "Başarısız.");
        });
        l.getChildren().addAll(lblInfo, new Label("TC:"), tTc, new Label("Tarih:"), dIn, dOut, btn);
        s.setScene(new Scene(l, 300, 350)); s.show();
    }

    private void addRoomToDB(int no, String t, double f, int k) throws Exception {
        Connection c = DatabaseConnection.getInstance().getConnection();
        c.prepareStatement("INSERT INTO rooms (oda_no, tip, fiyat, kapasite, durum) VALUES ("+no+", '"+t+"', "+f+", "+k+", 'MUSAIT')").executeUpdate();
    }
    private void deleteRoomFromDB(int no) { try { DatabaseConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM rooms WHERE oda_no="+no); }catch(Exception e){} }
    private void changeRoomStatus(int no, String st) { try { DatabaseConnection.getInstance().getConnection().createStatement().executeUpdate("UPDATE rooms SET durum='"+st+"' WHERE oda_no="+no); loadRooms(); }catch(Exception e){} }
    private String fetchUserNameByTC(String tc) { try { ResultSet rs=DatabaseConnection.getInstance().getConnection().createStatement().executeQuery("SELECT ad_soyad FROM users WHERE tc_no='"+tc+"'"); if(rs.next()) return rs.getString(1); }catch(Exception e){} return null; }

    private Button createWebButton(String t, String c, String h) {
        Button b = new Button(t);
        b.setTextFill(Color.WHITE); b.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        String s = "-fx-background-color: "+c+"; -fx-background-radius: 8; -fx-cursor: hand; -fx-padding: 8 15;";
        b.setStyle(s);
        b.setOnMouseEntered(e -> { b.setStyle("-fx-background-color: "+h+"; -fx-background-radius: 8; -fx-cursor: hand; -fx-padding: 8 15;"); ScaleTransition st=new ScaleTransition(Duration.millis(200), b); st.setToX(1.05); st.setToY(1.05); st.play(); });
        b.setOnMouseExited(e -> { b.setStyle(s); ScaleTransition st=new ScaleTransition(Duration.millis(200), b); st.setToX(1.0); st.setToY(1.0); st.play(); });
        return b;
    }
    private Button createSmallButton(String t, String c) {
        Button b = new Button(t); b.setTextFill(Color.WHITE); b.setStyle("-fx-background-color: "+c+"; -fx-font-size: 10px; -fx-padding: 3 8; -fx-cursor: hand; -fx-background-radius: 5;"); return b;
    }
    private TextField createStyledTextField(String p, int w) { TextField t = new TextField(); t.setPromptText(p); t.setPrefWidth(w); t.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #dfe6e9; -fx-padding: 8; -fx-text-fill: black;"); return t; } // SİYAH YAZI
    private Label createHeader(String t) { Label l = new Label(t); l.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22)); l.setTextFill(Color.DARKSLATEGRAY); return l; }
    private void addTextLimiter(TextField tf, int max) { tf.textProperty().addListener((o, old, nev) -> { if (nev.length() > max) tf.setText(old); }); }
    private void showAlert(String t, String m) { ModernAlert.show(t, m, t.contains("Hata") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION); }
}