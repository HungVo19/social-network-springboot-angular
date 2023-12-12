package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class AuthReq {
    @Schema(example = "user@email")
    private String email;
    @Schema(example = "123456")
    private String code;
}
