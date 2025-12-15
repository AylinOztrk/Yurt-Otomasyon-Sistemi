package com.yurt.patterns;

import com.yurt.model.Ogrenci;
import com.yurt.model.Personel;
import com.yurt.model.User;

public class UserFactory {

    // Static metod: Nesne üretmeden çağrılabilir
    public static User getUser(String role) {
        if (role.equalsIgnoreCase("OGRENCI")) {
            return new Ogrenci();
        } else if (role.equalsIgnoreCase("PERSONEL")) {
            return new Personel();
        }
        return null; // Tanımsız rol
    }
}