package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class LoginResp {
    @Schema(example = "200")
    private String code;
    @Schema(example = "user@email")
    private String email;
    @Schema(example = "OTP sent successfully")
    private String message;
    public LoginResp(String email) {
        this.code = "200";
        this.email = email;
        this.message = "OTP sent successfully";
    }
}
