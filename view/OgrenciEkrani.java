package com.yurt.view;

import com.yurt.db.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OgrenciEkrani extends JFrame {
    private String kullaniciAdi; // Tekrar kullanıcı adına döndük
    private JTable tblIzinler;
    private DefaultTableModel modelIzin;
    private DefaultTableModel modelOda;

    private JTextField txtTel, txtAdres;
    private JLabel lblOdaNo;

    // YENİ: Tarih Alanları
    private JTextField txtBaslangic, txtBitis;

    public OgrenciEkrani(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;

        setTitle("Öğrenci Paneli - " + kullaniciAdi);
        setSize(850, 600); // Biraz genişlettik
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // --- 1. SOL TARAF: PROFİL ---
        JLabel lblBaslik = new JLabel("Profil Bilgilerim");
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 14));
        lblBaslik.setBounds(20, 10, 200, 20);
        add(lblBaslik);

        add(new JLabel("Telefon:")).setBounds(20, 40, 80, 25);
        txtTel = new JTextField(); txtTel.setBounds(80, 40, 120, 25); add(txtTel);

        add(new JLabel("Adres:")).setBounds(20, 70, 80, 25);
        txtAdres = new JTextField(); txtAdres.setBounds(80, 70, 120, 25); add(txtAdres);

        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(80, 100, 120, 25);
        add(btnGuncelle);

        lblOdaNo = new JLabel("Oda: Yükleniyor...");
        lblOdaNo.setForeground(Color.BLUE);
        lblOdaNo.setBounds(20, 140, 200, 25);
        add(lblOdaNo);

        // Oda Arkadaşları
        add(new JLabel("Oda Arkadaşları:")).setBounds(20, 170, 200, 20);
        modelOda = new DefaultTableModel();
        modelOda.addColumn("Ad Soyad"); modelOda.addColumn("Tel");
        JTable tblOda = new JTable(modelOda);
        JScrollPane scrollOda = new JScrollPane(tblOda);
        scrollOda.setBounds(20, 190, 250, 150);
        add(scrollOda);

        // --- 2. SAĞ TARAF: İZİN İŞLEMLERİ ---
        JLabel lblIzinBaslik = new JLabel("İzin Talebi Oluştur");
        lblIzinBaslik.setFont(new Font("Arial", Font.BOLD, 14));
        lblIzinBaslik.setBounds(300, 10, 200, 20);
        add(lblIzinBaslik);

        // -- YENİ TARİH ALANLARI --
        add(new JLabel("Başlangıç (Yıl-Ay-Gün):")).setBounds(300, 40, 150, 25);
        txtBaslangic = new JTextField();
        txtBaslangic.setBounds(450, 40, 100, 25);
        // Bugünün tarihini varsayılan olarak yazalım
        txtBaslangic.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        add(txtBaslangic);

        add(new JLabel("Bitiş (Yıl-Ay-Gün):")).setBounds(560, 40, 120, 25);
        txtBitis = new JTextField();
        txtBitis.setBounds(680, 40, 100, 25);
        txtBitis.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        add(txtBitis);

        add(new JLabel("Neden:")).setBounds(300, 75, 50, 25);
        JTextField txtNeden = new JTextField();
        txtNeden.setBounds(350, 75, 430, 25);
        add(txtNeden);

        JButton btnIzinIste = new JButton("Talep Gönder");
        btnIzinIste.setBounds(350, 110, 150, 30);
        btnIzinIste.setBackground(Color.ORANGE);
        add(btnIzinIste);

        // Geçmiş İzinler Tablosu
        modelIzin = new DefaultTableModel();
        modelIzin.addColumn("Başlangıç"); modelIzin.addColumn("Bitiş");
        modelIzin.addColumn("Açıklama"); modelIzin.addColumn("Durum");
        tblIzinler = new JTable(modelIzin);
        JScrollPane scrollIzin = new JScrollPane(tblIzinler);
        scrollIzin.setBounds(300, 150, 480, 390);
        add(scrollIzin);

        // Verileri Yükle
        bilgileriGetir();
        izinleriListele();

        // Aksiyonlar
        btnGuncelle.addActionListener(e -> profilGuncelle(txtTel.getText(), txtAdres.getText()));
        btnIzinIste.addActionListener(e -> izinIste(txtBaslangic.getText(), txtBitis.getText(), txtNeden.getText()));
    }

    private void bilgileriGetir() {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            // WHERE username (Geri Döndü)
            String sql = "SELECT telefon, adres, oda_no FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kullaniciAdi);
            ResultSet rs = ps.executeQuery();

            String odaNo = "";
            if (rs.next()) {
                txtTel.setText(rs.getString("telefon"));
                txtAdres.setText(rs.getString("adres"));
                odaNo = rs.getString("oda_no");
                lblOdaNo.setText("Oda: " + (odaNo != null ? odaNo : "Yok"));
            }

            if (odaNo != null && !odaNo.isEmpty()) {
                modelOda.setRowCount(0);
                String sqlOda = "SELECT ad, soyad, telefon FROM users WHERE oda_no = ? AND username != ?";
                PreparedStatement psOda = conn.prepareStatement(sqlOda);
                psOda.setString(1, odaNo);
                psOda.setString(2, kullaniciAdi);
                ResultSet rsOda = psOda.executeQuery();
                while(rsOda.next()) {
                    modelOda.addRow(new Object[]{ rsOda.getString("ad") + " " + rsOda.getString("soyad"), rsOda.getString("telefon") });
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void profilGuncelle(String tel, String adres) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET telefon=?, adres=? WHERE username=?");
            ps.setString(1, tel); ps.setString(2, adres); ps.setString(3, kullaniciAdi);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Profil güncellendi!");
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void izinIste(String baslangic, String bitis, String aciklama) {
        if(aciklama.isEmpty()) { JOptionPane.showMessageDialog(this, "Lütfen neden belirtin!"); return; }
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement psId = conn.prepareStatement("SELECT id FROM users WHERE username=?");
            psId.setString(1, kullaniciAdi);
            ResultSet rsId = psId.executeQuery();

            if (rsId.next()) {
                int uid = rsId.getInt("id");
                // Artık CURDATE() yerine kullanıcının girdiği tarihleri kullanıyoruz
                String sql = "INSERT INTO izinler (ogrenci_id, baslangic_tarihi, bitis_tarihi, aciklama, durum) VALUES (?, ?, ?, ?, 'BEKLEMEDE')";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, uid);
                ps.setString(2, baslangic); // YYYY-MM-DD formatında olmalı
                ps.setString(3, bitis);
                ps.setString(4, aciklama);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Talep gönderildi!");
                izinleriListele();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata! Tarihi YYYY-MM-DD formatında (Örn: 2025-12-20) girdiğinizden emin olun.\n" + ex.getMessage());
        }
    }

    private void izinleriListele() {
        modelIzin.setRowCount(0);
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT i.baslangic_tarihi, i.bitis_tarihi, i.aciklama, i.durum FROM izinler i JOIN users u ON i.ogrenci_id = u.id WHERE u.username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kullaniciAdi);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                modelIzin.addRow(new Object[]{ rs.getDate("baslangic_tarihi"), rs.getDate("bitis_tarihi"), rs.getString("aciklama"), rs.getString("durum") });
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }
}