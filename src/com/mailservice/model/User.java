package com.mailservice.model;

import java.util.ArrayList;



public class User {

    private String name;
    private long contact;
    private String mail;
    private String dob;
    private String password;

    private ArrayList<Mail> sendMail = new ArrayList<>();
    private ArrayList<Mail> inboxMail = new ArrayList<>();
    private ArrayList<Mail> starredMail = new ArrayList<>();
    private ArrayList<Mail> draftMail = new ArrayList<>();
    private ArrayList<Mail> binMail = new ArrayList<>();

    public User(String name, long contact, String mail, String dob, String password) {
        super();
        this.name = name;
        this.contact = contact;
        this.mail = mail;
        this.dob = dob;
        this.password = password;
    }

    public String getMail() {
        return this.mail;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Mail> getInboxMail() {
        return this.inboxMail;
    }

    public ArrayList<Mail> getSendMail() {
        return this.sendMail;
    }

    public ArrayList<Mail> getDraftMail() {
        return this.draftMail;
    }

    public void sendMail(Mail send) {
        sendMail.add(send);
    }

    public void inboxMail(Mail inbox) {
        inboxMail.add(inbox);
    }

    public void draftMail(Mail draft) {
        draftMail.add(draft);
    }
    
    public ArrayList<Mail>getStarredMail(){
    	return this.starredMail;
    }
    
    public void addStarredMail(Mail mail)
    {
    	this.starredMail.add(mail);
    }
    public ArrayList<Mail> getBinMail() {
        return this.binMail;
    }

    public void moveToBin(Mail mail) {
        binMail.add(mail);
    }
    public long getContact() {
        return contact;
    }

    public String getDob() {
        return dob;
    }


}

