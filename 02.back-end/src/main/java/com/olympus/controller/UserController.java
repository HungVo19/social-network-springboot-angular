package com.olympus.controller;

import com.olympus.dto.*;
import com.olympus.service.IImageService;
import com.olympus.service.IMailService;
import com.olympus.service.IResetPwdTokenService;
import com.olympus.service.IUserService;
import com.olympus.validator.AppValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin("*")
@Tag(name = "Users", description = "User Management APIs")
@Validated
public class UserController {
    private final IUserService userService;
    private final IImageService iImageService;
    private final IMailService mailService;
    private final IResetPwdTokenService resetPwdTokenService;
    private final AppValidator appValidator;

    @Autowired
    public UserController(IUserService userService,
                          IImageService iImageService,
                          IMailService mailService,
                          IResetPwdTokenService resetPwdTokenService,
                          AppValidator validator) {
        this.userService = userService;
        this.iImageService = iImageService;
        this.mailService = mailService;
        this.resetPwdTokenService = resetPwdTokenService;
        this.appValidator = validator;
    }

    @PostMapping(value = "/register")
    @Operation(summary = "Register")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = RegistrationResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationReq request) {
        RegistrationResp response = userService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Login API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = LoginResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq request) throws MessagingException {
        String email = request.getEmail();
        mailService.sendOTP(email);
        return new ResponseEntity<>(new LoginResp(email), HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password")
    @Operation(summary = "Forgot password")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ForgotPwRsp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPwReq request) throws MessagingException {
        String email = request.getEmail();
        mailService.sendResetToken(email);
        ForgotPwRsp response = new ForgotPwRsp(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/validate-reset-password")
    @Operation(summary = "Validate reset password token")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ResetPwdTokenRsp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> verifyResetPwdToken(@RequestParam(value = "token", required = false)
                                                 @NotNull(message = "The token is invalid")
                                                 @NotBlank(message = "The token is invalid")
                                                 String token) {
        if (!resetPwdTokenService.existByToken(token)) {
            Map<String, String> message = new HashMap<>();
            message.put("Token", "Invalid");
            return new ResponseEntity<>(new ErrResp(message), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResetPwdTokenRsp(), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ChangePwdResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirements
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ChangePwdReq request) {
        userService.updatePwd(request);
        return new ResponseEntity<>(new ChangePwdResp(), HttpStatus.OK);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Update user's profiles")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = UpdateUserResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable
                                           @NotNull(message = "The Id is an invalid Id")
                                           @NotBlank(message = "The Id is an invalid Id")
                                           @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
                                           String id,
                                           @RequestPart @Valid
                                           UpdateUserReq updateUser,
                                           @RequestPart(required = false)
                                           MultipartFile file) throws IOException {
        ErrResp accessError = appValidator.validateCreatePostPermission(userDetails, id, updateUser.getId());
        if (accessError != null) {
            return new ResponseEntity<>(accessError, HttpStatus.FORBIDDEN);
        }

        String imageUrl = "";
        if (file != null) {
            ErrResp imageError = appValidator.validateImgFile(file);
            if (imageError != null) {
                return new ResponseEntity<>(imageError, HttpStatus.BAD_REQUEST);
            }
            String fileName = iImageService.save(file);
            imageUrl = iImageService.getImageUrl(fileName);
        }

        Long updateId = userService.updateUser(updateUser, imageUrl);
        UpdateUserResp response = new UpdateUserResp(updateId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
