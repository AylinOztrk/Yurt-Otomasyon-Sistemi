package com.yurt.view;

import com.yurt.db.DBConnection;
import com.yurt.patterns.IObserver;
import com.yurt.patterns.OgrenciObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PersonelEkrani extends JFrame {

    // --- Renk Paletimiz (Modern Flat Colors) ---
    private final Color RENK_MAVI = new Color(52, 152, 219);
    private final Color RENK_KOYU_MAVI = new Color(44, 62, 80);
    private final Color RENK_YESIL = new Color(46, 204, 113);
    private final Color RENK_KIRMIZI = new Color(231, 76, 60);
    private final Color RENK_TURUNCU = new Color(243, 156, 18);
    private final Color RENK_ARKAPLAN = new Color(236, 240, 241);

    private final Font FONT_BASLIK = new Font("Segoe UI", Font.BOLD, 18);
    private final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);

    // --- Modeller ---
    private DefaultTableModel modelIzinler;
    private DefaultTableModel modelOgrenciler;
    private DefaultTableModel modelOdalar;

    // --- Form Elemanları ---
    private JTextField txtOgrAd, txtOgrSoyad, txtOgrTC, txtOgrTel, txtOgrOda, txtOgrKadi, txtOgrSifre;
    private JTextField txtOdaNo, txtKapasite;

    public PersonelEkrani() {
        setTitle("Yurt Yönetim Paneli - Personel");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(RENK_ARKAPLAN); // Arka plan rengi

        // Ana Panel: Sekmeli Yapı
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);

        // --- 1. SEKME: İZİN YÖNETİMİ ---
        JPanel pnlIzin = createModernPanel();
        hazirlaIzinPaneli(pnlIzin);
        tabbedPane.addTab("📋 İzin Talepleri", pnlIzin);

        // --- 2. SEKME: ÖĞRENCİ YÖNETİMİ ---
        JPanel pnlOgrenci = createModernPanel();
        hazirlaOgrenciPaneli(pnlOgrenci);
        tabbedPane.addTab("🎓 Öğrenci İşlemleri", pnlOgrenci);

        // --- 3. SEKME: ODA YÖNETİMİ ---
        JPanel pnlOda = createModernPanel();
        hazirlaOdaPaneli(pnlOda);
        tabbedPane.addTab("🛏️ Oda İşlemleri", pnlOda);

        add(tabbedPane);
    }

    private JPanel createModernPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);
        return p;
    }

    // ==========================================
    // 1. İZİN YÖNETİMİ
    // ==========================================
    private void hazirlaIzinPaneli(JPanel panel) {
        JLabel lblBaslik = createLabel("Bekleyen İzin Talepleri", 20, 20, 300, 30, true);
        panel.add(lblBaslik);

        modelIzinler = new DefaultTableModel();
        String[] columns = {"ID", "Öğrenci", "Başlangıç", "Bitiş", "Açıklama", "Durum"};
        for (String col : columns) modelIzinler.addColumn(col);

        JTable tbl = createModernTable(modelIzinler);
        JScrollPane sc = new JScrollPane(tbl);
        sc.setBounds(20, 60, 890, 400);
        panel.add(sc);

        JButton btnOnayla = createButton("ONAYLA ✅", RENK_YESIL, 20, 480, 150, 40);
        panel.add(btnOnayla);

        JButton btnReddet = createButton("REDDET ❌", RENK_KIRMIZI, 190, 480, 150, 40);
        panel.add(btnReddet);

        JButton btnYenile = createButton("Listeyi Yenile 🔄", RENK_MAVI, 760, 480, 150, 40);
        panel.add(btnYenile);

        izinleriListele();

        btnYenile.addActionListener(e -> izinleriListele());
        btnOnayla.addActionListener(e -> izinIslem(tbl, "ONAYLANDI"));
        btnReddet.addActionListener(e -> izinIslem(tbl, "REDDEDILDI"));
    }

    // ==========================================
    // 2. ÖĞRENCİ YÖNETİMİ
    // ==========================================
    private void hazirlaOgrenciPaneli(JPanel panel) {
        panel.add(createLabel("Ad:", 20, 30, 60, 25, false));
        txtOgrAd = createTextField(60, 30, 140, 25); panel.add(txtOgrAd);

        panel.add(createLabel("Soyad:", 220, 30, 60, 25, false));
        txtOgrSoyad = createTextField(280, 30, 140, 25); panel.add(txtOgrSoyad);

        panel.add(createLabel("TC No:", 440, 30, 60, 25, false));
        txtOgrTC = createTextField(500, 30, 140, 25); panel.add(txtOgrTC);

        panel.add(createLabel("K.Adı:", 660, 30, 60, 25, false));
        txtOgrKadi = createTextField(720, 30, 140, 25); panel.add(txtOgrKadi);

        panel.add(createLabel("Tel:", 20, 70, 60, 25, false));
        txtOgrTel = createTextField(60, 70, 140, 25); panel.add(txtOgrTel);

        panel.add(createLabel("Oda:", 220, 70, 60, 25, false));
        txtOgrOda = createTextField(280, 70, 140, 25); panel.add(txtOgrOda);

        panel.add(createLabel("Şifre:", 440, 70, 60, 25, false));
        txtOgrSifre = createTextField(500, 70, 140, 25); panel.add(txtOgrSifre);

        JButton btnEkle = createButton("Öğrenci Kaydet 💾", RENK_TURUNCU, 20, 110, 200, 35);
        panel.add(btnEkle);

        modelOgrenciler = new DefaultTableModel();
        String[] cols = {"ID", "Ad Soyad", "TC", "Oda", "Tel"};
        for(String c : cols) modelOgrenciler.addColumn(c);

        JTable tbl = createModernTable(modelOgrenciler);
        JScrollPane sc = new JScrollPane(tbl);
        sc.setBounds(20, 160, 890, 350);
        panel.add(sc);

        ogrencileriListele();
        btnEkle.addActionListener(e -> ogrenciEkle());
    }

    // ==========================================
    // 3. ODA YÖNETİMİ
    // ==========================================
    private void hazirlaOdaPaneli(JPanel panel) {
        panel.add(createLabel("Oda Numarası:", 20, 40, 120, 30, false));
        txtOdaNo = createTextField(130, 40, 120, 30); panel.add(txtOdaNo);

        panel.add(createLabel("Kapasite:", 280, 40, 100, 30, false));
        txtKapasite = createTextField(360, 40, 120, 30); panel.add(txtKapasite);

        JButton btnOdaEkle = createButton("Oda Oluştur 🏠", RENK_MAVI, 520, 38, 180, 34);
        panel.add(btnOdaEkle);

        modelOdalar = new DefaultTableModel();
        modelOdalar.addColumn("Oda No");
        modelOdalar.addColumn("Kapasite");
        modelOdalar.addColumn("Mevcut Doluluk");

        JTable tbl = createModernTable(modelOdalar);
        JScrollPane sc = new JScrollPane(tbl);
        sc.setBounds(20, 100, 890, 410);
        panel.add(sc);

        odalariListele();
        btnOdaEkle.addActionListener(e -> odaEkle());
    }

    // --- MODERN UI YARDIMCI METODLARI ---

    private JLabel createLabel(String text, int x, int y, int w, int h, boolean isTitle) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x, y, w, h);
        lbl.setFont(isTitle ? FONT_BASLIK : FONT_NORMAL);
        lbl.setForeground(RENK_KOYU_MAVI);
        return lbl;
    }

    private JTextField createTextField(int x, int y, int w, int h) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, w, h);
        txt.setFont(FONT_NORMAL);
        return txt;
    }

    private JButton createButton(String text, Color bg, int x, int y, int w, int h) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, w, h);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false); // Tıklanınca çıkan çizgiyi kaldır
        btn.setBorderPainted(false); // Kenarlığı kaldır
        return btn;
    }

    private JTable createModernTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(30); // Satırları genişlet
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(220, 230, 241));
        table.setSelectionForeground(Color.BLACK);
        table.setShowGrid(false); // Dikey çizgileri kaldır (daha modern)
        table.setShowHorizontalLines(true);

        // Başlık Tasarımı
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(RENK_KOYU_MAVI);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setOpaque(true);

        return table;
    }

    // --- VERİTABANI İŞLEMLERİ (AYNEN KORUNDU) ---

    private void izinleriListele() {
        modelIzinler.setRowCount(0);
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT i.id, u.ad, u.soyad, i.baslangic_tarihi, i.bitis_tarihi, i.aciklama, i.durum FROM izinler i JOIN users u ON i.ogrenci_id = u.id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()) {
                modelIzinler.addRow(new Object[]{
                        rs.getInt("id"), rs.getString("ad") + " " + rs.getString("soyad"),
                        rs.getDate("baslangic_tarihi"), rs.getDate("bitis_tarihi"),
                        rs.getString("aciklama"), rs.getString("durum")
                });
            }
        } catch(Exception ex) { ex.printStackTrace(); }
    }

    private void izinIslem(JTable tbl, String durum) {
        int row = tbl.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir satır seçiniz!");
            return;
        }
        int id = (int) tbl.getValueAt(row, 0);
        String ogrAd = (String) tbl.getValueAt(row, 1);

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE izinler SET durum=? WHERE id=?");
            ps.setString(1, durum);
            ps.setInt(2, id);
            ps.executeUpdate();

            IObserver obs = new OgrenciObserver(ogrAd);
            obs.update("İzin talebiniz " + durum + " olarak güncellendi.");

            izinleriListele();
        } catch(Exception ex) { ex.printStackTrace(); }
    }

    private void ogrencileriListele() {
        modelOgrenciler.setRowCount(0);
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users WHERE role='OGRENCI'");
            while(rs.next()) {
                modelOgrenciler.addRow(new Object[]{
                        rs.getInt("id"), rs.getString("ad") + " " + rs.getString("soyad"),
                        rs.getString("tc_no"), rs.getString("oda_no"), rs.getString("telefon")
                });
            }
        } catch(Exception ex) { ex.printStackTrace(); }
    }

    private void odalariListele() {
        modelOdalar.setRowCount(0);
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM odalar");
            while(rs.next()) {
                modelOdalar.addRow(new Object[]{
                        rs.getString("oda_no"), rs.getInt("kapasite"), rs.getInt("doluluk")
                });
            }
        } catch(Exception ex) { ex.printStackTrace(); }
    }

    private void odaEkle() {
        if(txtOdaNo.getText().isEmpty()) return;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO odalar (oda_no, kapasite) VALUES (?, ?)");
            ps.setString(1, txtOdaNo.getText());
            ps.setInt(2, Integer.parseInt(txtKapasite.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Oda Eklendi!");
            odalariListele();
            txtOdaNo.setText(""); txtKapasite.setText("");
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }

    private void ogrenciEkle() {
        if(txtOgrAd.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Bilgileri doldurunuz!"); return; }

        String hedefOda = txtOgrOda.getText();
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            // Oda kontrolü
            PreparedStatement psOda = conn.prepareStatement("SELECT kapasite, doluluk FROM odalar WHERE oda_no=?");
            psOda.setString(1, hedefOda);
            ResultSet rsOda = psOda.executeQuery();

            if(rsOda.next()) {
                int kap = rsOda.getInt("kapasite");
                int dolu = rsOda.getInt("doluluk");
                if(dolu >= kap) {
                    JOptionPane.showMessageDialog(this, "⚠️ HATA: Bu oda tamamen dolu! Kapasite: " + kap);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ HATA: Böyle bir oda bulunamadı! Önce oda ekleyin.");
                return;
            }

            // Kayıt
            String sql = "INSERT INTO users (username, password, role, ad, soyad, tc_no, telefon, oda_no) VALUES (?, ?, 'OGRENCI', ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtOgrKadi.getText());
            ps.setString(2, txtOgrSifre.getText());
            ps.setString(3, txtOgrAd.getText());
            ps.setString(4, txtOgrSoyad.getText());
            ps.setString(5, txtOgrTC.getText());
            ps.setString(6, txtOgrTel.getText());
            ps.setString(7, hedefOda);
            ps.executeUpdate();

            // Doluluk artır
            PreparedStatement psUpd = conn.prepareStatement("UPDATE odalar SET doluluk = doluluk + 1 WHERE oda_no=?");
            psUpd.setString(1, hedefOda);
            psUpd.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Öğrenci Başarıyla Kaydedildi!");
            ogrencileriListele();
            odalariListele();

            // Temizlik
            txtOgrAd.setText(""); txtOgrSoyad.setText(""); txtOgrTC.setText("");
            txtOgrTel.setText(""); txtOgrKadi.setText(""); txtOgrSifre.setText("");

        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}