package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class LoginResponse {
    @Schema(example = "200")
    private String code;
    @Schema(example = "user@email")
    private String email;
    @Schema(example = "OTP sent successfully")
    private String message;
    public LoginResponse(String email) {
        this.code = "200";
        this.email = email;
        this.message = "OTP sent successfully";
    }
}
