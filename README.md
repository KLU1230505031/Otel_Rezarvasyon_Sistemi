🏨 Kırklareli Otel Yönetim Sistemi (Hotel Management System)
Bu proje, Java ve JavaFX kullanılarak geliştirilmiş, Nesne Yönelimli Programlama (OOP) prensiplerine ve Yazılım Tasarım Desenlerine (Design Patterns) sadık kalınarak mimarisi oluşturulmuş kapsamlı bir otel rezervasyon ve yönetim sistemidir.

✨ Özellikler

👤 Müşteri Paneli
Modern Arayüz: Kullanıcı dostu, kart yapılı ve responsive tasarım.

Oda Arama: Tarih aralığı, kişi sayısı ve oda tipine göre filtreleme.

Profil Yönetimi: Kişisel bilgileri güncelleme, avatar sistemi ve şifre değiştirme.

Rezervasyon: Seçilen odayı rezerve etme ve geçmiş rezervasyonları görüntüleme.

Dinamik Renkler: İsim baş harfine göre otomatik avatar rengi oluşturma.

🛠 Yönetim (Personel) Paneli
Dashboard: Otelin anlık doluluk oranlarını ve durumunu izleme.

Oda Yönetimi:

Oda Ekleme / Silme / Güncelleme.

Check-In (Giriş) ve Check-Out (Çıkış) işlemleri.

Odaların anlık durumunu (Müsait, Dolu, Rezerve) renkli kartlarla görme.

Müşteri Yönetimi: Kayıtlı tüm müşterileri listeleme, filtreleme ve detaylarını görme.

Rezervasyon Takibi:

Ödeme durumu takibi (Bekliyor / Ödendi).

Rezervasyon iptali veya onaylanması.

Renkli durum çubukları ile görsel takip.

Hızlı Aksiyonlar: Müşteri adına resepsiyondan rezervasyon oluşturma.

🏗 Mimari ve Kullanılan Teknolojiler
Bu proje, sadece çalışan bir uygulama değil, aynı zamanda temiz kod (clean code) prensiplerine uygun bir mimari örneğidir.

🔧 Teknolojiler
Dil: Java 21

Arayüz: JavaFX (FXML kullanılmadan, Saf Java kodu ile dinamik UI)

Veritabanı: MySQL

Stil: CSS (JavaFX CSS Styling)

📐 Tasarım Desenleri (Design Patterns)
Projede aşağıdaki desenler aktif olarak kullanılmıştır:

Singleton Pattern: Veritabanı bağlantısı (DatabaseConnection) için kullanıldı. Uygulama boyunca tek bir bağlantı nesnesi üzerinden işlem yapılarak kaynak yönetimi sağlandı.

Factory Method Pattern: RoomFactory sınıfı ile farklı tipteki odaların (Kral, Standart, Suit vb.) üretimi dinamik hale getirildi.

State Pattern: Odaların durumları (Available, Occupied, Reserved) birer sınıf olarak tasarlandı. Odanın davranışları (rezervasyon yapma, giriş yapma) bulunduğu duruma göre değişmektedir.

Facade Pattern: Karmaşık alt sistem işlemleri (Rezervasyon oluşturma, Loglama, Oda durumu güncelleme) HotelFacade sınıfı arkasında toplanarak arayüz katmanından soyutlandı.

Observer Pattern: Olay yönetimi ve veri takibi için kullanıldı. Bir odanın durumu değiştiğinde veya veritabanında bir güncelleme olduğunda, ilgili arayüz bileşenlerinin (UI) bu değişikliği algılayıp kendini otomatik olarak güncellemesi (Event Listeners & Bindings) bu desen sayesinde sağlandı.

🚀 Kurulum ve Çalıştırma
Projeyi yerel makinenizde çalıştırmak için adımları izleyin:

Projeyi Klonlayın

Veritabanını İçe Aktarın:

MySQL'de otel_db adında bir veritabanı oluşturun.

Proje dizinindeki database.sql dosyasını bu veritabanına import edin.

Bağlantı Ayarları:

src/com/otel/db/DatabaseConnection.java dosyasını açın.

Kendi veritabanı kullanıcı adı ve şifrenizi girin

📚 Gerekli Kütüphaneler ve İndirme Linkleri

Projeyi hatasız çalıştırabilmek için aşağıdaki harici kütüphaneleri indirip IDE'nize (Project Structure > Libraries) eklemeniz gerekmektedir:

1.  *JavaFX SDK (Sürüm 21 veya üzeri):*
    * 📥 [İndirmek için tıklayın (GluonHQ)](https://gluonhq.com/products/javafx/)
    * Not: İşletim sisteminize uygun olan **"SDK"* sürümünü indirin.*

2.  *MySQL Connector/J (JDBC Sürücüsü):*
    * 📥 [İndirmek için tıklayın (MySQL Resmi Sitesi)](https://dev.mysql.com/downloads/connector/j/)
    * Not: Listeden **"Platform Independent"* seçeneğini seçin ve ZIP dosyasını indirin. İçinden çıkan .jar uzantılı dosyayı kullanacaksınız.*

Çalıştırın:

LoginApp.java dosyasını çalıştırarak uygulamayı başlatın.

📝 Lisans
Bu proje eğitim amaçlı geliştirilmiştir. Kaynak gösterilerek kullanılabilir ve geliştirilebilir.

👨‍💻 İletişim

Geliştiriciler: 
[Murat  Aydoğan] LinkedIn: [https://www.linkedin.com/in/murat-aydo%C4%9Fan-51587b298/] 
[Kerem Yıldız] LinkedIn: [https://www.linkedin.com/in/kerem-y%C4%B1ld%C4%B1z-ba4bb1362/] GitHub: [https://github.com/KLU1230505031]
[Ömer Abalı] LinkedIn: [https://www.linkedin.com/in/omerabali/] GitHub: [https://github.com/omerabali]
                
