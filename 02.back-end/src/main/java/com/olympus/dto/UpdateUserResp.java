package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UpdateUserResp {
    @Schema(example = "200")
    private String code;
    @Schema(example = "1")
    private Long userId;
    @Schema(example = "Update user successfully")
    private String message;

    public UpdateUserResp(Long id) {
        this.code = "200";
        this.userId = id;
        this.message = "Update user successfully";
    }
}
