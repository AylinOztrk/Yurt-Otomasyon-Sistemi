package com.yurt.view;

import com.yurt.db.DBConnection;
import com.yurt.patterns.UserFactory; // Fabrikamızı ekledik
import com.yurt.model.User;           // Abstract sınıfımızı ekledik

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginEkrani extends JFrame {
    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JButton btnGiris;

    public LoginEkrani() {
        // --- PENCERE AYARLARI ---
        setTitle("Yurt Otomasyonu - Giriş");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Pencereyi ortalar
        setLayout(null);

        // --- BİLEŞENLER ---
        JLabel lblUser = new JLabel("T.C. Kimlik No:");
        lblUser.setBounds(50, 50, 100, 30);
        add(lblUser);

        txtKullaniciAdi = new JTextField();
        txtKullaniciAdi.setBounds(150, 50, 150, 30);
        add(txtKullaniciAdi);

        JLabel lblPass = new JLabel("Şifre:");
        lblPass.setBounds(50, 100, 100, 30);
        add(lblPass);

        txtSifre = new JPasswordField();
        txtSifre.setBounds(150, 100, 150, 30);
        add(txtSifre);

        btnGiris = new JButton("Giriş Yap");
        btnGiris.setBounds(150, 150, 100, 40);
        add(btnGiris);

        // --- BUTON TIKLAMA OLAYI ---
        btnGiris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                girisKontrol();
            }
        });
    }

    private void girisKontrol() {
        String kAdi = txtKullaniciAdi.getText();
        String sifre = new String(txtSifre.getPassword());

        try {
            // Veritabanı bağlantısını al (Singleton)
            Connection conn = DBConnection.getInstance().getConnection();

            // Kullanıcıyı sorgula
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kAdi);
            ps.setString(2, sifre);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("role"); // OGRENCI veya PERSONEL

                // --- FACTORY (FABRİKA) DESENİ BURADA DEVREYE GİRİYOR ---
                // Rol neyse ona uygun nesneyi fabrikadan istiyoruz.
                User currentUser = UserFactory.getUser(rol);

                if (currentUser != null) {
                    // Kullanıcı adını nesneye yüklüyoruz (Abstract sınıf özelliği)
                    currentUser.setUsername(kAdi);

                    JOptionPane.showMessageDialog(this, "Giriş Başarılı! Yönlendiriliyorsunuz...");

                    // POLİMORFİZM: Hangi sınıf olduğuna bakmaksızın ekranı aç diyoruz.
                    // O nesne kendi ekranını (OgrenciEkrani veya PersonelEkrani) açıyor.
                    currentUser.ekraniAc();

                    this.dispose(); // Login penceresini kapat
                } else {
                    JOptionPane.showMessageDialog(this, "Hata: Tanımsız Kullanıcı Rolü!");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Hatalı Kullanıcı Adı veya Şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı Hatası: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // İsteğe bağlı: Windows görünümü
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        new LoginEkrani().setVisible(true);
    }
}
