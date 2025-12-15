package com.yurt.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private String url = "jdbc:mysql://localhost:3307/YurtOtomasyonu";
    private String username = "root";
    private String password = "123456";

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Sürücüyü yükle
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}