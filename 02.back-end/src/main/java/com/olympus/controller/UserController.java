package com.olympus.controller;

import com.olympus.dto.*;
import com.olympus.service.IImageService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@Tag(name = "Users", description = "User Management APIs")
public class UserController {
    private final IUserService userService;
    private final IImageService iImageService;
    private final IMailService mailService;

    @Autowired
    public UserController(IUserService userService, IImageService iImageService, IMailService mailService) {
        this.userService = userService;
        this.iImageService = iImageService;
        this.mailService = mailService;
    }

    @PostMapping(value = "/register")
    @Operation(
            summary = "Register API",
            description = "Register a new user with email and password. " +
                    "The response returns a status code, new user's id and a success message. " +
                    "Otherwise return the error information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = RegistrationResponse.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @SecurityRequirements
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) {
        RegistrationResponse response = userService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    @Operation(
            summary = "Login API",
            description = "Log in with email and password. An OTP will be sent through email. " +
                    "The response returns a status code, new user's id and a success message. " +
                    "Otherwise return the error information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = LoginResponse.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @SecurityRequirements
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        String email = request.getEmail();
        mailService.sendOTP(email);
        return new ResponseEntity<>(new LoginResponse(email), HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password")
    @Operation(
            summary = "Forgot password API",
            description = "User click a link to reset password. A Token will be sent though user email. " +
                    "The response returns a status code, user's email and a success message. " +
                    "Otherwise return the error information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ForgotPwRsp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @SecurityRequirements
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPwReq request) throws MessagingException {
        String email = request.getEmail();
        mailService.sendResetToken(email);
        ForgotPwRsp response = new ForgotPwRsp(email);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/reset-password/{token}")
    public ResponseEntity<?> verifyResetPwdToken(@PathVariable(value = "token") String token) {

    }

//    @Operation(
//            description = "Update an user with image upload ability."
//    )
//    @PutMapping (
//            value = "/{id}",
//            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<?> signUp(@RequestPart User user,
//                                    @RequestPart(required = false, value = "file")
//                                    MultipartFile file) throws IOException {
//        if (file != null) {
//            String fileName = iImageService.save(file);
//            String imageUrl = iImageService.getImageUrl(fileName);
//            user.setAvatar(imageUrl);
//        }
//        User newUser = userService.save(user);
//        return new ResponseEntity<>(newUser, HttpStatus.OK);
//    }
}
