package com.mailservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.mailservice.db.DBConnection;
import com.mailservice.model.User;
import java.sql.ResultSet;

public class UserDAO {

    public void saveUser(User user) {

        String sql = "INSERT INTO users (name, email, password, contact, dob) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getMail());
            ps.setString(3, user.getPassword());
            ps.setLong(4, user.getContact());
            ps.setString(5, user.getDob());

            ps.executeUpdate();
            System.out.println("USER REGISTERED SUCCESSFULLY");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public User getUserByEmailAndPassword(String email, String password) {

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getString("name"),
                    rs.getLong("contact"),
                    rs.getString("email"),
                    rs.getString("dob"),
                    rs.getString("password")
                );
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // login failed
    }
    
    public boolean isUserExists(String email) {

        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // true if user exists

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
