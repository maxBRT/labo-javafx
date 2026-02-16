package com.github.maxbrt.films.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

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
            if (conn != null && !conn.isClosed()) {
                try {
                    File schemaFile = new File("schema.sql");
                    Scanner scanner = new Scanner(schemaFile);
                    StringBuilder sb = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        sb.append(scanner.nextLine()).append("\n");
                    }
                    scanner.close();
                    conn.createStatement().execute(sb.toString());
                    System.out.println("Migration applied successfully!");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        } catch (SQLException e) {
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
