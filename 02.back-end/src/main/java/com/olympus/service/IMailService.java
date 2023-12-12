package com.olympus.service;

import jakarta.mail.MessagingException;

public interface IMailService {
    public void sendEmail(String to, String subject, String text);
    public void sendOTP(String email) throws MessagingException;
    public void sendResetToken(String email) throws MessagingException;
}
