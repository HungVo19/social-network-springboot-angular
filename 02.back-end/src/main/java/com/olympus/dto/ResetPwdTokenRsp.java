package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class ResetPwdTokenRsp {
    @Schema(example = "200")
    private String code;
    @Schema(example = "Token is valid")
    private String message;
    public ResetPwdTokenRsp() {
        this.code = "200";
        this.message = "Token is valid";
    }
}
