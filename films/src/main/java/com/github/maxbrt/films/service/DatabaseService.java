package com.github.maxbrt.films.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseService {
    public static Connection connect() throws SQLException {
        Dotenv dotenv = Dotenv.load();
        String URL = dotenv.get("DATABASE_URL");
        String USER = dotenv.get("DATABASE_USER");
        String PASSWORD = dotenv.get("DATABASE_PASSWORD");

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void init() {
        try (Connection conn = connect()) {
            String script = Files.readString(Path.of("schema.sql"));

            try (Statement stmt = conn.createStatement()) {
                for (String sql : script.split(";")) {
                    String trimmedSql = sql.trim();
                    if (!trimmedSql.isEmpty()) {
                        stmt.execute(trimmedSql);
                    }
                }
            }
            System.out.println("Migration applied successfully!");

        } catch (IOException | SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void healthCheck() {
        try (Connection conn = connect()) {
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
    }
}
