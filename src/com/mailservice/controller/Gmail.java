package com.mailservice.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.mailservice.dao.MailDAO;
import com.mailservice.dao.UserDAO;
import com.mailservice.db.DBConnection;
import com.mailservice.model.Mail;
import com.mailservice.model.User;







public class Gmail {

    ArrayList<User> userList = new ArrayList<>();

    public void launchApplication() {

        for (;;) {
            System.out.println("\n *** WELCOME TO GMAIL *** ");
            System.out.println("1. CREATE ACCOUNT");
            System.out.println("2. LOGIN");
            System.out.println("3. EXIT");

            System.out.print("\nEnter your option : ");
            int option = new Scanner(System.in).nextInt();

            switch (option) {
            case 1 -> createAccount();
            case 2 -> login();
            case 3 -> System.exit(0);
            default -> System.out.println("\n INVALID OPTION ");
            }
        }
    }

    private void login() {

        System.out.println("\n LOGIN MODULE ");

        Scanner sc = new Scanner(System.in);

        System.out.print("Email ID : ");
        String userMail = sc.next();
        if (!userMail.endsWith("@gmail.com")) {
            userMail += "@gmail.com";
        }

        System.out.print("Password : ");
        String userPassword = sc.next();

        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmailAndPassword(userMail, userPassword);

        if (user != null) {
            homePage(user);
        } else {
            System.out.println("\n INVALID CREDENTIALS ");
        }
    }


    private void createAccount() {

        System.out.println("\n CREATE ACCOUNT MODULE ");

        System.out.print("First Name : ");
        String firstName = new Scanner(System.in).next();

        System.out.print("Last Name : ");
        String lastName = new Scanner(System.in).next();

        System.out.print("Contact : ");
        long contact = new Scanner(System.in).nextLong();

        String mail = null;

        outerLoop: for (;;) {
            System.out.print("Email : ");
            mail = new Scanner(System.in).next();
            if (!mail.endsWith("@gmail.com")) {
                mail += "@gmail.com";
            }


            for (User ele : userList) {
                if (mail.equals(ele.getMail())) {
                    System.out.println("\n MAIL-ID ALREADY EXISTS\n");
                    String[] suggestion = suggestions(firstName);
                    System.out.println(Arrays.toString(suggestion));
                    continue outerLoop;
                }
            }
            break;
        }

        System.out.print("DOB : ");
        String dob = new Scanner(System.in).next();

        System.out.print("Password : ");
        String password = new Scanner(System.in).next();

        User newUser = new User(firstName + " " + lastName, contact, mail, dob, password);
        UserDAO dao = new UserDAO();
        dao.saveUser(newUser);

    }

    private String[] suggestions(String name) {

        String[] suggestion = new String[3];

        for (int i = 0; i <= 2; i++) {

            String randomNumber = "";

            for (int j = 1; j <= 4; j++) {
                int dgt = (int) (Math.random() * 10);
                randomNumber += dgt;
            }

            String gmail = name + randomNumber + "@gmail.com";

            for (User ele : userList) {
                if (gmail.equals(ele.getMail())) {
                    i--;
                    continue;
                }
            }

            suggestion[i] = gmail;
        }

        return suggestion;
    }

    private void homePage(User user) {

        for (;;) {

            System.out.println("\n **** HOME PAGE MODULE **** ");
            System.out.println("1. Compose Mail");
            System.out.println("2. Draft");
            System.out.println("3. Send Mails");
            System.out.println("4. Inbox Mail");
            System.out.println("5. All Mail");
            System.out.println("6. Starred Mail");
            System.out.println("7. Bin");
            System.out.println("8. Logout");


            System.out.print("Enter an option : ");
            int option = new Scanner(System.in).nextInt();

            switch (option) {
            case 1 -> composeMail(user);
            case 2 -> draftModule(user);
            case 3 -> sendModule(user);
            case 4 -> inboxModule(user);
            case 5 -> allMailModule(user);
            case 6 -> starredMailModule(user);
            case 7 -> binModule(user);
            case 8 -> logout(user);

            }
        }
    }

    private void logout(User user) {
        System.out.println("\n THANK YOU " + user.getName() + " FOR USING GMAIL ");
        launchApplication();
    }
    private void starredMailModule(User user) {

        System.out.println("\n STARRED MAIL MODULE \n");

        MailDAO mailDao = new MailDAO();
        List<Mail> starredList = mailDao.getStarredMails(user.getMail());

        if (starredList.isEmpty()) {
            System.out.println("No Starred Mails!");
            return;
        }

        int i = 1;
        for (Mail mail : starredList) {
            System.out.println("Mail No : " + i++);
            mail.getMailInfo();
            System.out.println("_____________________________");
        }
    }


    private void allMailModule(User user) {
        System.out.println("\n ALL MAIL MODULE \n");
        sendModule(user);
        System.out.println("******************************");
        inboxModule(user);
    }

    private void inboxModule(User user) {

        System.out.println("\n INBOX MODULE \n");

        MailDAO mailDao = new MailDAO();
        List<Mail> inboxList = mailDao.getInboxMails(user.getMail());

        if (inboxList.isEmpty()) {
            System.out.println("Inbox is Empty!");
            return;
        }

        int index = 1;
        for (Mail mail : inboxList) {
            System.out.println("Mail No : " + index++);
            mail.getMailInfo();
            System.out.println("_____________________________");
        }

        // ⭐ STAR & 🗑️ DELETE WILL BE JDBC BASED (NEXT STEP)
    }



//    private void sendModule(User user) {
//        System.out.println("\n SEND MODULE \n");
//        ArrayList<Mail> sendList = user.getSendMail();
//
//        for (Mail ele : sendList) {
//            ele.getMailInfo();
//            System.out.println("_____________________________");
//        }
//    }

    private void draftModule(User fromUser) {

        System.out.println("\n DRAFT MAIL MODULE \n");

        ArrayList<Mail> draftList = fromUser.getDraftMail();

        if (draftList.isEmpty()) {
            System.out.println("No Draft Mails Found!");
            return;
        }

        int srno = 1;
        for (Mail ele : draftList) {
            System.out.println("Mail No : " + srno++);
            ele.getMailInfo();
            System.out.println("__________________________");
        }

        System.out.print("Do you want to Send/Edit Draft (yes/no): ");
        String resp = new Scanner(System.in).next();

        if (resp.equalsIgnoreCase("yes")) {

            System.out.print("Enter Draft Mail Number : ");
            int num = new Scanner(System.in).nextInt();

            if (num < 1 || num > draftList.size()) {
                System.out.println("Invalid Draft Number!");
                return;
            }

            Mail editSend = draftList.get(num - 1);

            editDraft(editSend);

            User toUser = null;
            MailDAO mailDao = new MailDAO();
            mailDao.sendMail(editSend);   // insert into DB
            draftList.remove(num - 1);
            System.out.println("\nMAIL HAS BEEN SENT SUCCESSFULLY FROM DRAFT\n");


            if (toUser != null) {
                fromUser.sendMail(editSend);
                toUser.inboxMail(editSend);
                draftList.remove(num - 1);
                System.out.println("\nMAIL HAS BEEN SENT SUCCESSFULLY FROM DRAFT\n");
            }
        }
    }

  

    private void composeMail(User user) {

        System.out.println("\n COMPOSE MAIL \n");
        System.out.println("From : " + user.getMail());

        Scanner sc = new Scanner(System.in);

        System.out.print("To : ");
        String toMail = sc.next();

        UserDAO userDao = new UserDAO();

        // check receiver exists in DB
        if (!userDao.isUserExists(toMail)) {
            System.out.println("\n USER NOT FOUND \n");
            return;
        }

        sc.nextLine(); // clear buffer

        System.out.print("Subject : ");
        String subject = sc.nextLine();

        System.out.print("Body : ");
        String body = sc.nextLine();

        Mail newMail = new Mail(user.getMail(), toMail, subject, body);

        System.out.print("DO U WANT TO SEND (yes/no): ");
        String resp = sc.next();

        if (resp.equalsIgnoreCase("yes")) {
            MailDAO mailDao = new MailDAO();
            mailDao.sendMail(newMail);   // JDBC INSERT
        } else {
            user.draftMail(newMail);     // still in memory
            System.out.println("Mail saved as Draft");
        }
    }

    private void editDraft(Mail editSend) {

        System.out.println("\n EDIT DRAFT MODULE \n");
        editSend.getMailInfo();

        System.out.println("\nWhat do you want to edit ?");
        System.out.println("1. Subject");
        System.out.println("2. Body");

        System.out.print("Enter your option : ");
        int opt = new Scanner(System.in).nextInt();

        Scanner sc = new Scanner(System.in);

        switch (opt) {
            case 1:
                System.out.print("Enter new Subject : ");
                editSend.setSubject(sc.nextLine());
                break;

            case 2:
                System.out.print("Enter new Body : ");
                editSend.setBody(sc.nextLine());
                break;

            default:
                System.out.println("Invalid Option!");
        }
    }
    private void binModule(User user) {

        System.out.println("\n BIN MAIL MODULE \n");

        MailDAO mailDao = new MailDAO();
        List<Mail> binList = mailDao.getBinMails(user.getMail());

        if (binList.isEmpty()) {
            System.out.println("Bin is Empty!");
            return;
        }

        int i = 1;
        for (Mail mail : binList) {
            System.out.println("Mail No : " + i++);
            mail.getMailInfo();
            System.out.println("_____________________________");
        }
    }


    
    private void sendModule(User user) {

        System.out.println("\n SENT MAIL MODULE \n");

        MailDAO mailDao = new MailDAO();
        List<Mail> sentList = mailDao.getSentMails(user.getMail());

        if (sentList.isEmpty()) {
            System.out.println("No Sent Mails!");
            return;
        }

        int i = 1;
        for (Mail mail : sentList) {
            System.out.println("Mail No : " + i++);
            mail.getMailInfo();
            System.out.println("_____________________________");
        }
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
    



}

