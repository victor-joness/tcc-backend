package com.tcc.api.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerErrorException;

@Component
@ConfigurationProperties(prefix = "strings")
public class SendMail{

    @Autowired
    protected JavaMailSender emailSender;

    @Value("${strings.fromEmail}")
    private String fromEmail;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlText = GenerateHTMLMessage.signup(text); // Gera o corpo HTML do e-mail

            helper.setFrom(fromEmail); // Importante: o Mailtrap exige um remetente
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlText, true); // true = envia como HTML

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new ServerErrorException("Erro ao enviar e-mail: " + e.getMessage(), e);
        }
    }
}
