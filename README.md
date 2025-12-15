# 🏢 Yurt Otomasyon Sistemi

Bu proje, Yazılım Mühendisliği "Görsel Programlama" dersi kapsamında geliştirilmiş kapsamlı bir **Yurt Yönetim Otomasyonu**dur. 

## Proje Ekibi
Projenin geliştirilmesinde görev alan ekip üyeleri:

* **[Aylin Öztürk]** - [AylinOztrk](https://github.com/AylinOztrk)
* **[Ayşegül Avcı]** - [KLU5230505062](https://github.com/KLU5230505062)

## Projenin Amacı ve Özellikleri
Bu sistem, üniversite yurtlarındaki kayıt, oda ve izin süreçlerini dijitalleştirmeyi amaçlar.
* **Yönetici Paneli:** Öğrenci kaydı yapabilir, silinmesi gereken kayıtları silebilir, oda durumlarını görebilir ve izin taleplerini onaylayabilir/reddedebilir.
* **Öğrenci Paneli:** Kendi profilini görebilir, oda arkadaşlarını görüntüleyebilir ve izin talebi oluşturup durumunu takip edebilir.
* **Veritabanı Entegrasyonu:** Tüm veriler MySQL veritabanında güvenli bir şekilde saklanır.

## Kullanılan Teknolojiler ve Mimari
* **Dil:** Java (Swing & AWT Arayüz Kütüphaneleri)
* **Veritabanı:** MySQL
* **IDE:** IntelliJ IDEA
* **Tasarım Desenleri (Design Patterns):**
    * **Singleton:** Veritabanı bağlantısının tek bir nesne üzerinden yönetilmesi için.
    * **Factory:** Kullanıcı nesnelerinin (Öğrenci/Personel) dinamik üretimi için.
    * **Observer:** İzin durumu değişikliklerinin bildirilmesi için.
    * **Facade:** Karmaşık alt sistemlerin tek bir arayüzden başlatılması için.

## Kurulum ve Çalıştırma (Hocamız İçin Notlar)
Projeyi kendi bilgisayarınızda çalıştırmak için şu adımları izleyebilirsiniz:

1. **Veritabanı Kurulumu:** Proje dosyalarındaki `veritabani.sql` dosyasını MySQL Workbench ile import ediniz.
2. **Bağlantı Ayarı:** `src/com/yurt/db/DBConnection.java` dosyasındaki şifre kısmını kendi yerel veritabanı şifrenizle güncelleyiniz.
3. **Başlatma:** `Main.java` dosyasını çalıştırarak giriş ekranına ulaşabilirsiniz.
   * **Yönetici Girişi:** TC: `999` / Şifre: `1234` (Örnek)
   * **Öğrenci Girişi:** TC: `111` / Şifre: `1234` (Örnek)
