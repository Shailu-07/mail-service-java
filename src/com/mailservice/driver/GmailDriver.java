package com.mailservice.driver;

import com.mailservice.controller.Gmail;

public class GmailDriver {

    public static void main(String[] args) {
        Gmail gmail = new Gmail();
        gmail.launchApplication();
    }
}
