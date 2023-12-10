package com.olympus.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;

public interface IMailService {
    public void sendEmail(String to, String subject, String text);
    public void sendOTP(String email);
    public void sendResetToken(String email) throws MessagingException;
}
