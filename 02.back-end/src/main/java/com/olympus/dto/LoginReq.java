package com.olympus.dto;

import com.olympus.validator.annotation.ExistUserByEmailAndPass;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
@ExistUserByEmailAndPass
public class LoginReq {
    @NotNull
    @NotBlank
    @Schema(example = "user@email")
    private String email;
    @NotNull
    @NotBlank
    @Schema(example = "123456")
    private String password;
}
