package com.olympus.dto;

import com.olympus.validator.annotation.ExistUserId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema
public class FrdReqCrt {
    @Schema(example = "1")
    @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
    @NotBlank(message = "The id is required")
    @ExistUserId
    private String senderId;
    @Schema(example = "2")
    @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
    @NotBlank(message = "The id is required")
    @ExistUserId
    private String receiverId;
}
