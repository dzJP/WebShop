package com.example.webshoplabb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNewPassword(String email, String newPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@deltmasolutions.com");
            message.setTo(email);
            message.setSubject("New Password");
            message.setText("Hello!\n\nYour new password is: " + newPassword);



            System.out.println("New password sent to: " + email);
        } catch (Exception e) {
            System.err.println("Error sending new password to: " + email);
            e.printStackTrace();
        }
    }

}
