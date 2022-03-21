package com.dasha.usersystem.email;

public interface EmailSender {
    void sendToConfirm(String toEmail, String link);
    void sendWhenConfirmed(String toEmail);
}
