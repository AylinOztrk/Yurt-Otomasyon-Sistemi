package com.yurt.patterns;

import com.yurt.db.DBConnection;
import com.yurt.view.LoginEkrani;
import javax.swing.UIManager;

// FACADE DESENİ: Sistemi başlatmak için tek bir basit kapı sunar.
public class YurtSistemiFacade {

    public void sistemiBaslat() {
        System.out.println("🔌 Sistem Facade üzerinden başlatılıyor...");

        // 1. Adım: Görünümü Güzelleştir (Look and Feel)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("✅ Arayüz teması yüklendi.");
        } catch (Exception e) {
            System.out.println("⚠️ Tema yüklenemedi, varsayılan kullanılıyor.");
        }

        // 2. Adım: Veritabanı Bağlantısını Kontrol Et (Singleton)
        if (DBConnection.getInstance().getConnection() != null) {
            System.out.println("✅ Veritabanı bağlantısı kontrol edildi: BAŞARILI");
        } else {
            System.out.println("❌ Veritabanı bağlantısı BAŞARISIZ! Lütfen ayarları kontrol edin.");
        }

        // 3. Adım: Giriş Ekranını Aç
        new LoginEkrani().setVisible(true);
        System.out.println("🚀 Login ekranı açıldı.");
    }
}