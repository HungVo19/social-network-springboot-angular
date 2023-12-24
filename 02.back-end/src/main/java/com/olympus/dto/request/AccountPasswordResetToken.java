package com.olympus.dto.request;

import com.olympus.validator.annotation.resetPasswordToken.ExistResetPasswordToken;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema
public class AccountPasswordResetToken {
    @Schema(example = "@2134ds%&*?J")
    @NotBlank(message = "Token is invalid")
    @ExistResetPasswordToken
    private String token;
}
