package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
public class ForgotPwReq {
    @NotNull(message = "The email is required")
    @NotBlank(message = "The email is required")
    @Schema(example = "user@email")
    private String email;

}
