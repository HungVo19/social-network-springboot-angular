package com.olympus.service.impl;

import com.olympus.entity.User;
import com.olympus.repository.IUserRepository;
import com.olympus.service.IAuthenticationService;
import com.olympus.service.IResetPwdTokenService;
import com.olympus.service.IUserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {
    @InjectMocks
    MailServiceImpl mailService;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private IUserService userService;
    @Mock
    private IAuthenticationService authenticationService;
    @Mock
    private IResetPwdTokenService resetPwdTokenService;
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private IUserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

//    @Test
//    public void testSendLoginOTP_Success() throws MessagingException {
//        // Arrange
//        String email = "user@example.com";
//        User user = new User();
//        user.setId(1L);
//        user.setEmail(email);
//        when(userService.findUserByEmail(email)).thenReturn(Optional.of(user));
//
//        // Act
//        mailService.sendLoginOTP(email);
//
//        // Assert
//        verify(authenticationService).createAuthentication(eq(user), anyString());
//        verify(mailSender).send(mimeMessage);
//    }
}