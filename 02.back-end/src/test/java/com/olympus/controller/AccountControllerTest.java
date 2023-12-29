package com.olympus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olympus.dto.request.*;
import com.olympus.service.IMailService;
import com.olympus.service.IResetPwdTokenService;
import com.olympus.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IUserService userService;

    @MockBean
    private IMailService mailService;

    @MockBean
    private IResetPwdTokenService resetPwdTokenService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(springSecurity()).build();
    }

    @WithMockUser(value = "spring")
    @Test
    void testRegister_Success() throws Exception {
        //Arrange
        AccountRegister validAccount = new AccountRegister();
        validAccount.setEmail("user@email.com");
        validAccount.setPassword("password");
        when(userService.register(any(AccountRegister.class))).thenReturn(1L);

        //Act & Assert
        mockMvc.perform(post("/v1/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(validAccount)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "spring")
    void testLogin_Success() throws Exception {
        //Arrange
        AccountLogin validLogin = new AccountLogin();
        validLogin.setEmail("user@email.com");
        validLogin.setPassword("123456");
        when(userService.existsUserByEmailAndPassword(validLogin)).thenReturn(true);
        doNothing().when(mailService).sendLoginOTP(anyString());

        //Act & Assert
        mockMvc.perform(post("/v1/account/login").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(validLogin))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void testForgotPassword_SenTokenSuccess() throws Exception {
        //Arrange
        AccountPasswordForgot accountPasswordForgot = new AccountPasswordForgot();
        accountPasswordForgot.setEmail("user@email.com");
        doNothing().when(mailService).sendPasswordResetToken(anyString());

        //Act & Assert
        mockMvc.perform(post("/v1/account/forgot-password").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountPasswordForgot))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void testValidateResetPasswordToken() throws Exception {
        //Arrange
        AccountPasswordResetToken accountPasswordResetToken = new AccountPasswordResetToken();
        accountPasswordResetToken.setEmail("user@email.com");
        accountPasswordResetToken.setToken("token");
        when(userService.existsEmail(accountPasswordResetToken.getEmail())).thenReturn(true);
        when(resetPwdTokenService.existByTokenAndEmail(accountPasswordResetToken.getToken(), accountPasswordResetToken.getEmail())).thenReturn(true);
        doNothing().when(resetPwdTokenService).reset(anyString());

        //Act & Assert
        mockMvc.perform(post("/v1/account/validate-reset-password").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountPasswordResetToken))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void testResetPassword() throws Exception {
        //Arrange
        AccountPasswordReset accountPasswordReset = new AccountPasswordReset();
        accountPasswordReset.setEmail("user@email.com");
        accountPasswordReset.setPassword("123456");
        when(userService.existsEmail(accountPasswordReset.getEmail())).thenReturn(true);

        doNothing().when(userService).updatePassword(accountPasswordReset);

        //Act && Assert
        mockMvc.perform(post("/v1/account/reset-password").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountPasswordReset))).andExpect(status().isOk());
    }
}