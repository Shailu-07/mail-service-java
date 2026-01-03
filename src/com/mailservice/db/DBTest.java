package com.mailservice.db;

import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();
            System.out.println("DATABASE CONNECTED SUCCESSFULLY");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
