package com.example.otp1r4.dao;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCConnection {

    static Connection connection = null;
    static PreparedStatement preparedStatement = null;

    private static String username;
    private static String password;

    public static void main(String[] args) {
        readCredentials();
        makeJDBCConnection();
    }

    private static void readCredentials() {
        File connectionFile = new File("src/main/java/com/example/otp1r4/dao/credentials.security");
        assert !connectionFile.exists() : "Security file is non existent. Contact your administrator.";

        try {
            Scanner scanner = new Scanner(connectionFile);

            username = scanner.nextLine();
            password = scanner.nextLine();

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void makeJDBCConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            connection = DriverManager.getConnection("jdbc:mariadb://mysql.metropolia.fi:3306/ilkkakar", username, password);
            if (connection != null) {
                System.out.println("Connection Successful!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }
    }
}
