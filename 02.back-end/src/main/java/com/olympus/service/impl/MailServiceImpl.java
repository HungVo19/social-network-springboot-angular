package com.olympus.service.impl;

import com.olympus.config.Constant;
import com.olympus.entity.Authentication;
import com.olympus.entity.ResetPwdToken;
import com.olympus.entity.User;
import com.olympus.service.IAuthenticationService;
import com.olympus.service.IMailService;
import com.olympus.service.IResetPwdTokenService;
import com.olympus.service.IUserService;
import com.olympus.utils.AppUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class MailServiceImpl implements IMailService {
    private final JavaMailSender mailSender;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IAuthenticationService authenticationService;
    private final IResetPwdTokenService resetPwdTokenService;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender,
                           IUserService userService,
                           PasswordEncoder passwordEncoder,
                           IAuthenticationService authenticationService,
                           IResetPwdTokenService resetPwdTokenService) {
        this.mailSender = mailSender;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.resetPwdTokenService = resetPwdTokenService;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Constant.MailSenderAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendOTP(String email) {
        String code = AppUtils.generateRandomOTP();
        String subject = "Verification";
        String text = "Your verification code is " + code;
        sendEmail(email, subject, text);

        User user = userService.findUserByEmail(email).get();
        authenticationService.deleteByUser(user);
        String encryptedCode = passwordEncoder.encode(code);
        Authentication authentication = new Authentication(user, encryptedCode);
        authenticationService.save(authentication);
    }

    @Override
    public void sendResetToken(String email) throws MessagingException {
        String token = UUID.randomUUID().toString();
        User user = userService.findUserByEmail(email).get();

        resetPwdTokenService.deleteByUser(user);
        String encryptedToken = passwordEncoder.encode(token);
        ResetPwdToken resetToken = new ResetPwdToken(user, encryptedToken);
        resetPwdTokenService.save(resetToken);

        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress(Constant.MailSenderAddress));
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("Reset Password");
        String htmlContent = "<p> Click link below to reset password</p>" +
                "<br>" +
                "<a href=\"http://localhost:8080/users/reset-password?token=" +token + "\">Reset</a>";
        message.setContent(htmlContent, "text/html; charset=utf-8");
        mailSender.send(message);
    }
}
