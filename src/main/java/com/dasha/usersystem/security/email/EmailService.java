package com.dasha.usersystem.security.email;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@NoArgsConstructor
public class EmailService implements EmailSender{
    @Value("${spring.mail.username}")
    private String email;
    @Autowired private JavaMailSender mailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    @Override
    @Async
    public void sendToConfirm(String toEmail, String url) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(toEmail);
            helper.setSubject("Подтверждение email");
            
            helper.setText("Активация аккаунта по ссылке ниже:\n"+url);


            helper.setFrom(email);
            mailSender.send(mimeMessage);
        } catch (MessagingException e){
            LOGGER.error("sending messages failed", e);
            throw new IllegalStateException("sending message failed");
        }
    }
    public void sendWhenConfirmed(String toEmail){
        try{
        MimeMessage helloMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(helloMessage, "utf-8");
            helper.setFrom(email);
            helper.setTo(toEmail);
            helper.setSubject("Аккаунт подтверждён");
            helper.setText("");
            mailSender.send(helloMessage);
        } catch (MessagingException e){
            LOGGER.error("sending message after confirmation failed", e);
            throw new IllegalStateException("sending message failed");
        }
    }
}
