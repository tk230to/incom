package com.incom.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailUtils {

    @Autowired
    private MailSender mailSender;

    public void send(String to, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("from@example.com");
        message.setSubject("てすと件名");
        message.setText(text);
        this.mailSender.send(message);

    }
}
