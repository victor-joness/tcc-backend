package com.tcc.api.config.mail;


import com.tcc.api.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private SendMail sendMail;

    public void sendSimpleMessage(String to, String subject, String text) {
        sendMail.sendSimpleMessage(to, subject, text);
    }
}
