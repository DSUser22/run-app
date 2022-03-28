package com.dasha.usersystem.security.email;

public interface EmailSender {
    void sendToConfirm(String toEmail, String link);
    void sendWhenConfirmed(String toEmail);
}
