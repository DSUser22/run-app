package com.dasha.usersystem.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private JavaMailSender mailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    @Async
    public void sendToConfirm(String toEmail, String url) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(toEmail);
            helper.setSubject("Подтверждение email");
            /*helper.setText(String.format("Здравствуй, %s!\n" +
                    "Спасибо за регистрацию. Пожалуйста, активируй аккаун по ссылке ниже\n" +
                    "%s\n" +
                    "Ссылка активна 15 минут", name, url));*/
            helper.setText("Активация аккаунта по ссылке ниже:\n" +
                    ""+url);


            helper.setFrom("havegoodrunnings@gmail.com");
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
            helper.setFrom("havegoodrunnings@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Аккаунт подтверждён");
            helper.setText("");
            //helper.setText(name+", спасибо за регистрацию! Хороших пробежек :)");
            mailSender.send(helloMessage);
        } catch (MessagingException e){
            LOGGER.error("sending message after confirmation failed", e);
            throw new IllegalStateException("sending message failed");
        }
    }
}
