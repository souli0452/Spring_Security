package com.sm.sm_project.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    private JavaMailSender mailSender;

    public EmailSendService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public  void sendEmail(String toEmail,String subject,String body){
        SimpleMailMessage message= new SimpleMailMessage();
        message.setFrom("clemens63@ethereal.email");
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("mail envoye");
    }
}
