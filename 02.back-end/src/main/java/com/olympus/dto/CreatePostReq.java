package com.olympus.dto;

import com.olympus.entity.Privacy;
import com.olympus.validator.annotation.ExistUserId;
import com.olympus.validator.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema
public class CreatePostReq {
    @Schema(example = "1")
    @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
    @NotBlank(message = "The id is required")
    @ExistUserId
    private String userId;
    @Schema(example = "Hello World")
    @NotBlank(message = "The content cannot be blank")
    private String content;
    @Schema(example = "public")
    @NotBlank(message = "The privacy cannot be blank")
    @ValidEnum(enumClass = Privacy.class, message = "The privacy is not valid")
    private String privacy;
}
