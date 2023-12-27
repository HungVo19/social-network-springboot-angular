package com.olympus.service;

import jakarta.mail.MessagingException;

public interface IMailService {
    public void sendEmail(String to, String subject, String text);

    public void sendLoginOTP(String email) throws MessagingException;

    public void sendPasswordResetToken(String email) throws MessagingException;
}
