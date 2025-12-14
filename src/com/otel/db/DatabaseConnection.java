package com.otel.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Singleton Instance
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // MySQL sürücüsü ve veritabanı yolu
            String url = "jdbc:mysql://localhost:3308/otel_db";
            String user = "root"; // Kendi MySQL kullanıcı adınız
            String password = ""; // Kendi MySQL şifreniz
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else {
            try {
                if (instance.getConnection().isClosed()) {
                    instance = new DatabaseConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}