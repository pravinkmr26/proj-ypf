/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.services;

import com.pravin.models.Mail;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pravinkumar
 */
@Service
public class Mailer {

    @Autowired
    JavaMailSender mailer;

    public boolean sendEmail(Mail mail) throws MailException, Exception {
        try {
            MimeMessage msg = mailer.createMimeMessage();
            msg.setContent(mail.getDescription(), "text/html");
            msg.setFrom(new InternetAddress("pravinkumar2629@gmail.com"));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getToAddress()));
            msg.setSubject(mail.getSubject());
            msg.setText(mail.getDescription());
            mailer.send(msg);
            return true;
        } catch (MessagingException | MailException  ex) {
            System.out.println("Error " + ex.getMessage());
            return false;
        }
        catch (Exception  ex) {
            System.out.println("Error " + ex.getMessage());
            return false;
        }
    }
}
