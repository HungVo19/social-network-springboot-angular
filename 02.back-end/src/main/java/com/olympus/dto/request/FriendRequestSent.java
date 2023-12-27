package com.olympus.dto.request;

import com.olympus.validator.annotation.user.ExistUserById;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema
public class FriendRequestSent {
    @Schema(example = "2")
    @ExistUserById
    private Long receiverId;
}
