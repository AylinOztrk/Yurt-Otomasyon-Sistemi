package com.yurt.patterns;

import javax.swing.JOptionPane;

public class OgrenciObserver implements IObserver {

    private String ogrenciAdi;

    public OgrenciObserver(String ogrenciAdi) {
        this.ogrenciAdi = ogrenciAdi;
    }

    @Override
    public void update(String mesaj) {
        // Gerçek hayatta burada e-posta veya SMS atılır.
        // Biz simülasyon olarak ekrana mesaj çıkaracağız veya konsola yazacağız.
        System.out.println("📧 SMS Gönderildi -> Sayın " + ogrenciAdi + ": " + mesaj);

        // Görsel olarak da görelim:
        JOptionPane.showMessageDialog(null, "SİSTEM BİLDİRİMİ:\nSayın " + ogrenciAdi + "\n" + mesaj);
    }
}