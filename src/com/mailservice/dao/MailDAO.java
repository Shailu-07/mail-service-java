package com.mailservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.mailservice.db.DBConnection;
import com.mailservice.model.Mail;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class MailDAO{

    public void sendMail(Mail mail) {

        String sql = "INSERT INTO mails (sender_email, receiver_email, subject, body, status) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mail.getSender());
            ps.setString(2, mail.getReceiver());
            ps.setString(3, mail.getSubject());
            ps.setString(4, mail.getBody());
            ps.setString(5, "SENT");

            ps.executeUpdate();
            System.out.println("MAIL SENT SUCCESSFULLY");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Mail> getInboxMails(String receiverEmail) {

        List<Mail> inbox = new ArrayList<>();

        String sql = "SELECT mail_id, sender_email, receiver_email, subject, body "
                   + "FROM mails WHERE receiver_email = ? AND status = 'SENT'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, receiverEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mail mail = new Mail(
                    rs.getInt("mail_id"),
                    rs.getString("sender_email"),
                    rs.getString("receiver_email"),
                    rs.getString("subject"),
                    rs.getString("body")
                );
                inbox.add(mail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inbox;
    }

    public void starMail(int mailId) {

        String sql = "UPDATE mails SET status = 'STARRED' WHERE mail_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mailId);
            ps.executeUpdate();

            System.out.println("Mail Starred Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveToBin(int mailId) {

        String sql = "UPDATE mails SET status = 'BIN' WHERE mail_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mailId);
            ps.executeUpdate();

            System.out.println("Mail moved to Bin");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Mail> getSentMails(String senderEmail) {

        List<Mail> sentList = new ArrayList<>();

        String sql = "SELECT mail_id, sender_email, receiver_email, subject, body " +
                     "FROM mails WHERE sender_email = ? AND status = 'SENT'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, senderEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mail mail = new Mail(
                    rs.getInt("mail_id"),
                    rs.getString("sender_email"),
                    rs.getString("receiver_email"),
                    rs.getString("subject"),
                    rs.getString("body")
                );
                sentList.add(mail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sentList;
    }
    public List<Mail> getStarredMails(String userEmail) {

        List<Mail> starred = new ArrayList<>();

        String sql = "SELECT mail_id, sender_email, receiver_email, subject, body " +
                     "FROM mails WHERE (sender_email = ? OR receiver_email = ?) " +
                     "AND status = 'STARRED'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ps.setString(2, userEmail);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mail mail = new Mail(
                    rs.getInt("mail_id"),
                    rs.getString("sender_email"),
                    rs.getString("receiver_email"),
                    rs.getString("subject"),
                    rs.getString("body")
                );
                starred.add(mail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return starred;
    }

    public List<Mail> getBinMails(String userEmail) {

        List<Mail> bin = new ArrayList<>();

        String sql = "SELECT mail_id, sender_email, receiver_email, subject, body " +
                     "FROM mails WHERE (sender_email = ? OR receiver_email = ?) " +
                     "AND status = 'BIN'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ps.setString(2, userEmail);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mail mail = new Mail(
                    rs.getInt("mail_id"),
                    rs.getString("sender_email"),
                    rs.getString("receiver_email"),
                    rs.getString("subject"),
                    rs.getString("body")
                );
                bin.add(mail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bin;
    }

    



}
