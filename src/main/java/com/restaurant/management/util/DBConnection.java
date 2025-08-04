package com.restaurant.management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/restaurant_db";
    private static final String USER = "vigneshk";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.postgresql.Driver"); // Optional in newer JDBC, but safe
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the PostgreSQL database.");
            e.printStackTrace();
            throw new RuntimeException("Unable to connect to DB", e);
        }
    }
}
