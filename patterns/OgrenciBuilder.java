package com.yurt.patterns;

import com.yurt.model.Ogrenci;

public class OgrenciBuilder {
    // Üreteceğimiz nesne
    private Ogrenci ogrenci;

    public OgrenciBuilder() {
        this.ogrenci = new Ogrenci();
    }

    // Zincirleme metodlar (Her biri 'this' döndürür)
    public OgrenciBuilder ad(String ad) {
        // Ogrenci sınıfında 'ad' alanı varsa set ederiz,
        // yoksa şimdilik username üzerinden simüle edelim veya boş geçelim
        // Gerçek projede: this.ogrenci.setAd(ad); olurdu.
        return this;
    }

    public OgrenciBuilder kullaniciAdi(String kAdi) {
        this.ogrenci.setUsername(kAdi);
        return this;
    }

    // Nesneyi son haline getirip teslim eden metod
    public Ogrenci build() {
        return this.ogrenci;
    }
}