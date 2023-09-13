package com.example.emailwebsite;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class main {
    public static void main(String[] args) {
        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        try {
            int randomPIN = (int) (Math.random() * 9000) + 1000;
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("hoangtung120801@gmail.com");
            msg.setTo("hoangtung120801@gmail.com");
            msg.setSubject("OTP-Code");
            msg.setText("Hello \n\n" + "Your Login OTP :" + randomPIN + ".Please Verify. \n\n" + "Regards \n" + "Tung");
            javaMailSender.send(msg);
            System.out.println("Success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
}
