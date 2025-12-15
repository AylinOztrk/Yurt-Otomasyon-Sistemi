package com.yurt.model;

import com.yurt.view.PersonelEkrani;

public class Personel extends User {
    @Override
    public void ekraniAc() {
        // Personel kendi ekranını başlatır
        System.out.println("Fabrika: Personel nesnesi üretildi, ekran açılıyor...");
        new PersonelEkrani().setVisible(true);
    }
}