package com.yurt.model;

public abstract class User {
    protected String username;

    // Kullanıcı adını ayarlamak için metod
    public void setUsername(String username) {
        this.username = username;
    }

    // Her kullanıcının kendi ekranını açmasını sağlayan soyut metod
    // (Bunu alt sınıflar doldurmak ZORUNDA)
    public abstract void ekraniAc();
}