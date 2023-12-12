package com.olympus.controller;

import com.olympus.config.AuthDetailsImpl;
import com.olympus.config.jwt.JwtProvider;
import com.olympus.dto.AuthReq;
import com.olympus.dto.AuthResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Tag(name = "Authentication", description = "Authentication Management APIs")
public class AuthController {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JwtProvider jwtProvider,
                          AuthenticationManager authenticationManager) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @Operation(
            summary = "Authenticate an user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = AuthResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    content = {@Content(schema = @Schema(),
                            mediaType = "application/json")}),
    })
    @SecurityRequirements
    @PostMapping(value = "/v1/auth")
    public AuthResp authenticateUser(@RequestBody AuthReq authReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReq.getEmail(),
                        authReq.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken((AuthDetailsImpl) authentication.getPrincipal());
        return new AuthResp("bearer", jwt);
    }

    @Operation(
            summary = "Test authentication",
            description = "API for testing valid authentication"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    content = {@Content(schema = @Schema(),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/v1/auth/test")
    public ResponseEntity<?> testAuthenticate() {
        Map<String, String> text = new HashMap<>();
        text.put("OK", "User is authenticated");
        return new ResponseEntity<>(text, HttpStatus.OK);
    }
}
