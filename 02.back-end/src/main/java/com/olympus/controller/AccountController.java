package com.olympus.controller;

import com.olympus.config.Constant;
import com.olympus.dto.request.*;
import com.olympus.dto.response.BaseResponse;
import com.olympus.service.IAuthenticationService;
import com.olympus.service.IMailService;
import com.olympus.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin("*")
@Tag(name = "Account", description = "User's account management APIs")
@Validated
public class AccountController {
    private final IUserService userService;
    private final IMailService mailService;
    private final IAuthenticationService authenticationService;

    @Autowired
    public AccountController(IUserService userService,
                             IMailService mailService,
                             IAuthenticationService authenticationService) {
        this.userService = userService;
        this.mailService = mailService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register")
    @Operation(summary = "Register new account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> register(@RequestBody @Valid AccountRegister accountRegister) {
        Long newUserId = userService.register(accountRegister);
        Map<String, Long> data = new HashMap<>();
        data.put("id", newUserId);
        BaseResponse<Map<String, Long>, ?> response =
                BaseResponse.success(HttpStatus.CREATED, Constant.MSG_SUCCESS_ACCOUNT_REGISTER, data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> login(@RequestBody @Valid AccountLogin accountLogin) throws MessagingException {
        String email = accountLogin.getEmail();
        mailService.sendOTP(email);
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        BaseResponse<Map<String, String>, ?> response =
                BaseResponse.success(HttpStatus.OK, Constant.MSG_SUCCESS_ACCOUNT_OTP_SENT, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password")
    @Operation(summary = "Forgot password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody AccountPasswordForgot request) throws MessagingException {
        String email = request.getEmail();
        mailService.sendPasswordResetToken(email);
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        BaseResponse<Map<String, String>, ?> response =
                BaseResponse.success(HttpStatus.OK, Constant.MSG_SUCCESS_ACCOUNT_PWD_RESET_TOKEN_SENT, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/validate-reset-password")
    @Operation(summary = "Validate reset password token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> validateResetPasswordToken(@RequestBody @Valid AccountPasswordResetToken token) {
        BaseResponse<String, ?> response =
                BaseResponse.success(HttpStatus.OK, Constant.MSG_SUCCESS_ACCOUNT_PWD_RESET_TOKEN_VALIDATE, HttpStatus.NO_CONTENT.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> resetPassword(@RequestBody @Valid AccountPasswordReset request) {
        userService.updatePassword(request);
        BaseResponse<String, ?> response =
                BaseResponse.success(HttpStatus.OK, Constant.MSG_SUCCESS_ACCOUNT_PWD_RESET, HttpStatus.NO_CONTENT.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
