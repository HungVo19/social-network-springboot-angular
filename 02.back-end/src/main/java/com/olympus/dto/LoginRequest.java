package com.olympus.dto;

import com.olympus.validator.ValidUserByEmailAndPass;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
@ValidUserByEmailAndPass
public class LoginRequest {
    @NotNull
    @NotBlank
    @Schema(example = "user@email")
    private String email;
    @NotNull
    @NotBlank
    @Schema(example = "123456")
    private String password;
}
