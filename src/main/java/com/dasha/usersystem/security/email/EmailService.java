package com.dasha.usersystem.security.email;

import com.dasha.usersystem.exception.registration.SendingMessageException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EmailService implements EmailSender{
    @Value("${spring.mail.username}")
    private String email;
    @Autowired private JavaMailSender mailSender;
    @Override
    @Async
    public void sendToConfirm(String toEmail, String url) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(toEmail);
            helper.setSubject("Confirmation email");

            helper.setText("Click on the link to activate your account:\n"+url);


            helper.setFrom(email);
            mailSender.send(mimeMessage);
        } catch (MessagingException e){
            throw new SendingMessageException("sending message failed");
        }
    }
    public void sendWhenConfirmed(String toEmail) {
        try{
        MimeMessage helloMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(helloMessage, "utf-8");
            helper.setFrom(email);
            helper.setTo(toEmail);
            helper.setSubject("Account confirmed");
            helper.setText("");
            mailSender.send(helloMessage);
        } catch (MessagingException e){
            log.error("sending message after confirmation failed ", e);
            throw new SendingMessageException("sending message failed");
        }
    }
}
