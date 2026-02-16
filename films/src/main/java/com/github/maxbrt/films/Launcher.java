package com.github.maxbrt.films;

import java.sql.Connection;
import java.sql.SQLException;

import com.github.maxbrt.films.service.DatabaseService;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        try (Connection conn = DatabaseService.connect()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connected to database");

                var statement = conn.createStatement();
                var resultSet = statement.executeQuery("SELECT version();");
                if (resultSet.next()) {
                    System.out.println("DB Version: " + resultSet.getString(1));
                }
            } else {
                System.out.println("Failed to connect to database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Application.launch(HelloApplication.class, args);
    }
}
