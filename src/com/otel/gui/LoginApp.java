package com.otel.gui;

import com.otel.db.DatabaseConnection;
import javafx.application.Application;
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

public class LoginApp extends Application {

    // Arka plan resmi
    private final String BACKGROUND_URL = "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=1920&auto=format&fit=crop";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kırklareli Otel - Giriş Sistemi");

        // --- ARKA PLAN ---
        Image image = new Image(BACKGROUND_URL);
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImage));

        // --- GİRİŞ KARTI ---
        VBox loginCard = new VBox(20);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setMaxWidth(400);
        loginCard.setPadding(new Insets(40));
        // Beyaz Şeffaf Kart
        loginCard.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.90), new CornerRadii(15), Insets.EMPTY)));
        loginCard.setEffect(new DropShadow(20, Color.rgb(0,0,0,0.3)));

        // Başlıklar
        Label lblTitle = new Label("Giriş Yap");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        lblTitle.setTextFill(Color.DARKSLATEGRAY);

        Label lblSub = new Label("Otel sistemine erişmek için giriş yapın.");
        lblSub.setTextFill(Color.GRAY);

        // Alanlar
        TextField txtKullanici = createStyledField("T.C. / E-posta / Telefon/Ad Soyad ");
        PasswordField txtSifre = new PasswordField();
        txtSifre.setPromptText("Şifreniz");
        txtSifre.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 5;");

        Button btnGiris = new Button("GÜVENLİ GİRİŞ");
        btnGiris.setMaxWidth(Double.MAX_VALUE);
        btnGiris.setStyle("-fx-background-color: #2d3436; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12px; -fx-cursor: hand; -fx-background-radius: 5;");

        Hyperlink linkKayit = new Hyperlink("Hesabınız yok mu? Hemen Kayıt Olun");
        linkKayit.setStyle("-fx-text-fill: #0984e3; -fx-border-color: transparent; -fx-font-size: 13px;");

        Label lblMesaj = new Label();

        loginCard.getChildren().addAll(lblTitle, lblSub, new Separator(), txtKullanici, txtSifre, btnGiris, linkKayit, lblMesaj);
        root.getChildren().add(loginCard);

        // Olaylar
        btnGiris.setOnAction(e -> login(txtKullanici.getText(), txtSifre.getText(), lblMesaj));
        linkKayit.setOnAction(e -> showRegisterWindow());

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Tam ekran açılış
        primaryStage.show();
    }

    // --- MODERN KAYIT PENCERESİ (GÜNCELLENDİ: TELEFON EKLENDİ) ---
    private void showRegisterWindow() {
        Stage regStage = new Stage();
        regStage.initModality(Modality.APPLICATION_MODAL);
        regStage.setTitle("Yeni Hesap Oluştur");

        // Arka Plan
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundImage(new Image(BACKGROUND_URL), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true))));

        // Kayıt Kartı
        VBox regCard = new VBox(15);
        regCard.setAlignment(Pos.CENTER);
        regCard.setPadding(new Insets(30));
        regCard.setMaxWidth(380);
        // Beyaz Şeffaf Kart
        regCard.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.95), new CornerRadii(15), Insets.EMPTY)));
        regCard.setEffect(new DropShadow(20, Color.rgb(0,0,0,0.4)));

        Label lblTitle = new Label("Aramıza Katılın");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTitle.setTextFill(Color.DARKSLATEGRAY);

        // --- ALANLAR ---
        TextField txtAd = createStyledField("Ad Soyad");
        TextField txtTc = createStyledField("T.C. Kimlik No");
        TextField txtMail = createStyledField("E-posta Adresi");

        // >> YENİ EKLENEN ALAN: TELEFON <<
        TextField txtTel = createStyledField("Telefon Numarası (5XX...)");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Şifre Belirleyin");
        txtPass.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 5;");

        Button btnKaydet = new Button("KAYDI TAMAMLA");
        btnKaydet.setMaxWidth(Double.MAX_VALUE);
        btnKaydet.setStyle("-fx-background-color: #00b894; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12px; -fx-cursor: hand; -fx-background-radius: 5;");

        regCard.getChildren().addAll(lblTitle, new Separator(), txtAd, txtTc, txtMail, txtTel, txtPass, btnKaydet);
        root.getChildren().add(regCard);

        btnKaydet.setOnAction(e -> {
            // Boş alan kontrolü (Telefon da eklendi)
            if(txtAd.getText().isEmpty() || txtTc.getText().isEmpty() || txtTel.getText().isEmpty() || txtPass.getText().isEmpty()) {
                ModernAlert.show("Eksik Bilgi", "Lütfen tüm alanları doldurunuz.", Alert.AlertType.WARNING);
                return;
            }

            // >> SQL GÜNCELLENDİ: 'telefon' KOLONU EKLENDİ <<
            String sql = "INSERT INTO users (tc_no, ad_soyad, email, telefon, sifre, rol) VALUES (?, ?, ?, ?, ?, 'MUSTERI')";

            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, txtTc.getText());
                pstmt.setString(2, txtAd.getText());
                pstmt.setString(3, txtMail.getText());
                pstmt.setString(4, txtTel.getText()); // 4. Soru işareti Telefon oldu
                pstmt.setString(5, txtPass.getText()); // 5. Soru işareti Şifre oldu

                pstmt.executeUpdate();

                ModernAlert.show("Hoşgeldiniz!", "Kaydınız başarıyla oluşturuldu. Giriş yapabilirsiniz.", Alert.AlertType.INFORMATION);
                regStage.close();

            } catch (Exception ex) {
                ex.printStackTrace(); // Hata detayını konsolda görmek için
                ModernAlert.show("Hata", "Kayıt yapılamadı. TC, Email veya Telefon zaten kayıtlı olabilir.", Alert.AlertType.ERROR);
            }
        });

        Scene scene = new Scene(root, 500, 700); // Pencere boyutunu biraz uzattık
        regStage.setScene(scene);
        regStage.show();
    }

    // Login metodu
    private void login(String girilenBilgi, String sifre, Label lblMesaj) {
        String query = "SELECT * FROM users WHERE (tc_no = ? OR email = ? OR telefon = ? OR ad_soyad = ?) AND sifre = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, girilenBilgi);
            pstmt.setString(2, girilenBilgi);
            pstmt.setString(3, girilenBilgi);
            pstmt.setString(4, girilenBilgi); // Ad Soyad kontrolü için
            pstmt.setString(5, sifre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("rol");
                String ad = rs.getString("ad_soyad");
                String tcNo = rs.getString("tc_no");
                ((Stage) lblMesaj.getScene().getWindow()).close();
                if (rol.equals("MUSTERI")) new CustomerDashboard().show(ad, tcNo);
                else new PersonnelDashboard().show(ad);
            } else {
                ModernAlert.show("Giriş Başarısız", "Hatalı bilgi veya şifre girdiniz.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private TextField createStyledField(String prompt) {
        TextField t = new TextField();
        t.setPromptText(prompt);
        t.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-radius: 5;");
        return t;
    }

    public static void main(String[] args) { launch(args); }
}