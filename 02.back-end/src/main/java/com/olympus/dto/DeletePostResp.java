package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class DeletePostResp {
    @Schema(example = "200")
    private String code;
    @Schema(example = "Delete successfully")
    private String message;

    public DeletePostResp() {
        this.code = "200";
        this.message = "Delete post successfully";
    }
}
