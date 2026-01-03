package com.mailservice.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3306/mail_service_db";
        String user = "root";
        String password = "shailesh"; // put your MySQL password

        return DriverManager.getConnection(url, user, password);
    }
}
