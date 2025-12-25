# 🏨 Kırklareli Otel Yönetim Sistemi (Hotel Management System)

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-red?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/JavaFX-GUI-blue?style=for-for-the-badge"/>
  <img src="https://img.shields.io/badge/MySQL-Database-orange?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/OOP-Clean%20Code-success?style=for-the-badge"/>
</p>

---

## 📌 Proje Tanımı: Akademik ve Sektörel Nitelik

**Kırklareli Otel Yönetim Sistemi**, Java ve JavaFX kullanılarak geliştirilmiş, kapsamlı bir **otel rezervasyon ve yönetim otomasyonudur**.

Bu proje, sadece çalışan bir masaüstü uygulamasının ötesinde; **Nesne Yönelimli Programlama (OOP)** prensiplerine, **Clean Code** yaklaşımına ve **Yazılım Tasarım Desenlerine** sadık kalınarak mimarisi oluşturulmuş, **akademik ve sektörel nitelikte** bir yazılım örneğidir. Proje, **katmanlı mimari**, **modüler yapı** ve **tasarım desenlerinin gerçek senaryolar üzerinden uygulanışını** başarıyla göstermektedir.

---

## 🏗 Mimari, Teknolojiler ve Tasarım Desenleri

Proje, **genişletilebilir, bakımı kolay ve test edilebilir** bir yapı sağlamak amacıyla katmanlı mimari yaklaşımını temel alır.

### 🔧 Kullanılan Teknolojiler

| Alan | Teknoloji | Açıklama |
| :--- | :--- | :--- |
| **Programlama Dili** | Java 21+ | Projenin temel dili. |
| **Arayüz (GUI)** | JavaFX | FXML kullanılmadan, **saf Java kodu ile dinamik arayüz** geliştirme. |
| **Veritabanı** | MySQL | Güçlü ve ilişkisel veri depolama çözümü. |
| **Stil** | JavaFX CSS | Modern ve tutarlı kullanıcı deneyimi için stil yönetimi. |
| **IDE** | IntelliJ IDEA | Tercih edilen geliştirme ortamı. |

---

### 📐 Kullanılan Tasarım Desenleri (Design Patterns)


| Tasarım Deseni | Kullanım Amacı |
| :--- | :--- |
| **Singleton** | `DatabaseConnection` sınıfı ile **veritabanı bağlantısının uygulama boyunca tekil** olarak yönetilmesi (Kaynak optimizasyonu). |
| **Factory Method** | `RoomFactory` ile farklı oda tiplerinin (**Standart, Suit, Kral**) **esnek ve dinamik** bir şekilde üretilmesi. |
| **State** | Odaların durumlarının (`Available`, `Occupied`, `Reserved`) nesne bazlı yönetilmesi ve **davranışların duruma göre otomatik değişmesi**. |
| **Facade** | Karmaşık alt sistem işlemlerinin (Rezervasyon, loglama, durum güncelleme) `HotelFacade` altında toplanarak **arayüz katmanından soyutlanması**. |
| **Observer** | Veri veya durum değişimlerinde (Örn: Oda Durumu) **arayüz bileşenlerinin (UI) otomatik ve anlık güncellenmesi**. |
| **Builder**| `ReservationTicket` gibi **çok sayıda parametre alan karmaşık nesneleri** oluştururken, okunabilirliği artırmak ve **parametrik sıralamadan kaynaklanan hataları önlemek**. |

---

## ✨ Sistem Özelliklerine Derinlemesine Bakış

### 👤 Müşteri Paneli
* **Modern ve Kullanıcı Dostu Arayüz:** Kart tabanlı, sade ve okunabilir tasarım anlayışı.
* **Akıllı Oda Arama:** Tarih aralığı, kişi sayısı ve oda tipine göre filtreleme yaparak yalnızca **müsait odaların** listelenmesi.
* **Rezervasyon Yönetimi:** Seçilen odanın rezerve edilmesi ve geçmiş/aktif rezervasyonların görüntülenmesi.
* **Profil Yönetimi:** Kişisel bilgilerin güncellenmesi ve şifre değiştirme.
* **Dinamik Avatar Sistemi:** Kullanıcı adının ilk harfine göre **otomatik renk üreten** görsel avatar yapısı.

---

### 🛠 Yönetim (Personel) Paneli
* **Dashboard (Kontrol Paneli):** Otelin anlık doluluk durumu ve genel durum takibi.
* **Oda Yönetimi:**
    * Oda ekleme, silme ve güncelleme işlemleri.
    * **Check-In (Giriş) & Check-Out (Çıkış) işlemleri.**
    * Odaların durumlarını (**Müsait / Dolu / Rezerve**) **renkli kartlar ile görsel takip**.
* **Müşteri Yönetimi:** Tüm kayıtlı müşterilerin listelenmesi, filtrelenmesi ve detay görüntüleme.
* **Rezervasyon Takibi:**
    * Ödeme durumu kontrolü (**Bekliyor / Ödendi**).
    * Rezervasyon onaylama veya iptal işlemleri.
    * Görsel durum çubukları ile hızlı takip.
* **Hızlı Aksiyonlar:** Resepsiyon tarafından müşteri adına doğrudan rezervasyon oluşturabilme yeteneği.

---

## 🎯 Proje Kazanımları

Bu projeyi geliştirerek ve bu projede yer alarak, ekip aşağıdaki kritik konularda pratik deneyim kazanmıştır:

* **Katmanlı Mimari:** MVC yapılarının ve katmanlı mimarinin gerçek bir projede uygulanması.
* **Tasarım Desenleri:** Singleton, Factory, State, Facade, Observer ve **Builder** desenlerinin senaryo bazlı, amaca yönelik kullanımı.
* **Modern GUI Geliştirme:** JavaFX ile FXML kullanmadan, saf Java kodu üzerinden modern ve dinamik masaüstü arayüz geliştirme.
* **Veritabanı Entegrasyonu:** MySQL ile ilişkisel veritabanı entegrasyonu ve JDBC kullanımı.
* **Sürdürülebilirlik:** Temiz kod (Clean Code) prensipleri ile sürdürülebilir yazılım geliştirme yaklaşımları.

---

## 🚀 Kurulum ve Çalıştırma

Projeyi yerel makinenizde hızlıca çalıştırmak için adımları izleyin:

1.  **Projeyi Klonlayın:**
    ```bash
    git clone [GitHub Adresiniz Buraya Gelecek]
    ```
    *(Not: Lütfen bu kısma projenizin ana GitHub repository linkini ekleyiniz.)*
2.  **Veritabanını Yapılandırın:**
    * MySQL sunucunuzda **`otel_db`** adında bir veritabanı oluşturun.
    * Proje dizinindeki **`database.sql`** dosyasını bu veritabanına import edin.
3.  **Veritabanı Bağlantı Ayarları:**
    * Aşağıdaki dosyadan kendi veritabanı kullanıcı adı ve şifrenizi girin:
        ```text
        src/com/otel/db/DatabaseConnection.java
        ```
4.  **Uygulamayı Başlatın:**
    * IntelliJ IDEA'da projeyi açın ve ana sınıf olan `LoginApp.java` dosyasını çalıştırın.

---

## 👨‍💻 Geliştirici Ekibi

| Geliştirici | GitHub | LinkedIn |
| :--- | :--- | :--- |
| **Murat Aydoğan** | 🔗 https://github.com/murataydogan | 🔗 https://www.linkedin.com/in/murat-aydo%C4%9Fan-51587b298/ |
| **Kerem Yıldız** | 🔗 https://github.com/yldz1kerem | 🔗 https://www.linkedin.com/in/kerem-y%C4%B1ld%C4%B1z-ba4bb1362?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app / |
| **Ömer Abalı** | 🔗 https://github.com/omerabali | 🔗 https://www.linkedin.com/in/omerabali/ |

---
