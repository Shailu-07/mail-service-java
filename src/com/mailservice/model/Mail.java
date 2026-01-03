package com.mailservice.model;

public class Mail {

    private int mailId;          // DB primary key
    private String sender;
    private String receiver;
    private String subject;
    private String body;

    // ✅ Constructor for DB FETCH (Inbox, Star, Bin)
    public Mail(int mailId, String sender, String receiver, String subject, String body) {
        this.mailId = mailId;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    // ✅ Constructor for NEW mail (Compose / Draft)
    public Mail(String sender, String receiver, String subject, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public int getMailId() {
        return mailId;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    // setters (for editing draft)
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void getMailInfo() {
        System.out.println("*** MAIL ***");
        System.out.println("Receiver : " + receiver);
        System.out.println("Sender   : " + sender);
        System.out.println("Subject  : " + subject);
        System.out.println("Body     : " + body);
    }
}
