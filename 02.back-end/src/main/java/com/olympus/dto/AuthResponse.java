package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema
public class AuthResponse {
    @Schema(example = "bearer")
    private String message;
    @Schema(example = "eyJhbGciOiJIUzUxMiJ9")
    private String accessToken;
}
