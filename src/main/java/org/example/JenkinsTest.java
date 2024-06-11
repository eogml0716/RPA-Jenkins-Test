package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JenkinsTest {
    public static void main(String[] args) {
        System.out.println("Hello, Jenkins!");
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
                Statement statement = connection.createStatement();
                statement.execute("CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY, name TEXT)");
                statement.execute("INSERT INTO test (name) VALUES ('Jenkins')");
                System.out.println("Database setup complete.");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
