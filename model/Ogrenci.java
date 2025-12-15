package com.yurt.model;

import com.yurt.view.OgrenciEkrani;

public class Ogrenci extends User {
    @Override
    public void ekraniAc() {
        // Öğrenci kendi ekranını başlatır
        System.out.println("Fabrika: Öğrenci nesnesi üretildi, ekran açılıyor...");
        new OgrenciEkrani(username).setVisible(true);
    }
}