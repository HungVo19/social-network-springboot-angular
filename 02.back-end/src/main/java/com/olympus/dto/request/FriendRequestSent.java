package com.olympus.dto.request;

import com.olympus.validator.annotation.user.ExistUserById;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Getter
@Schema
public class FriendRequestSent {
    @Schema(example = "2")
//    @NotBlank(message = "Invalid user Id")
//    @Pattern(regexp = "^[1-9]\\d*$", message = "Receiver not found")
    @ExistUserById
    private Long receiverId;
}
