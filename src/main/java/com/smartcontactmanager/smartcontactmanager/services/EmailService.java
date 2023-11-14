package com.smartcontactmanager.smartcontactmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public boolean sendEmail(String to , String content , String subject){
        boolean result = false;

        try{
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom("manashjyotihandique449@gmail.com");
            message.setTo(to); 
            message.setSubject(subject); 
            message.setText(content);
            emailSender.send(message);
            result = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
        
    }
}
