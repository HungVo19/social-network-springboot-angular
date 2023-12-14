package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UpdatePostResp {
    @Schema(example = "200")
    private String code;
    @Schema(example = "123")
    private Long postId;
    @Schema(example = "Update post successfully")
    private String message;

    public UpdatePostResp(Long id) {
        this.code = "200";
        this.postId = id;
        this.message = "Update post successfully";
    }
}
