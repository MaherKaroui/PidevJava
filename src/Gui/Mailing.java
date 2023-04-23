/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


/**
 *
 * @author saada
 */
public class Mailing {
     public static void sendEmail(String recipient, String comment) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String username = "chaima.saadallah@esprit.tn"; 
        String password = "Salah1960"; 

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = prepareMessage(session, username, recipient, comment);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String username, String recipient, String comment) throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject("Nouveau commentaire");
        message.setText("Vous avez reçu un nouveau commentaire tu vas l'approuver ? : " + comment);
        return message;
    }
    
    
    
    
    
    public static void checkInbox(String username, String password) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");
        properties.setProperty("mail.imap.host", "imap.gmail.com");
        properties.setProperty("mail.imap.port", "993");

        Session session = Session.getInstance(properties);

        Store store = session.getStore("imaps");
        store.connect(username, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();

        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            System.out.println("From: " + message.getFrom()[0].toString());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Date: " + message.getSentDate());
            System.out.println();
        }

        inbox.close(false);
        store.close();
    }
}
