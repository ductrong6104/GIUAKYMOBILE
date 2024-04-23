package com.example.kiemtragiuaky.model;

import android.util.Log;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender extends javax.mail.Authenticator {
    private String EMAIL;
    private String PASSWORD;

    public EmailSender() {
    }

    public EmailSender(String EMAIL, String PASSWORD) {
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public void sendOTP(String recipientEmail, String subject, String body) {
        // Step 1: Set up the mail session properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        // Step 2: Get the default Session object
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        // Step 3: Create a new MimeMessage object
        try {
            MimeMessage message = new MimeMessage(session);

            // Step 4: Set From Address
            message.setFrom(new InternetAddress(EMAIL));

            // Step 5: Set To Address
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Step 6: Set Subject
            message.setSubject(subject);

            // Step 7: Set Body
            message.setText(body);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        // Step 8: Send the email
                        Transport.send(message);
                    }
                    catch (MessagingException e){
                        e.printStackTrace();
                    }
                }
            });
            thread.start();


            Log.d("Send mail", "gui ma otp sang mail thanh cong");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
