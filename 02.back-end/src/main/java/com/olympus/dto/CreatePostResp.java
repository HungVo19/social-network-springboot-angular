package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class CreatePostResp {
    @Schema(example = "201")
    private String code;
    @Schema(example = "123")
    private Long postId;
    @Schema(example = "Create new post successfully")
    private String message;

    public CreatePostResp(Long id) {
        this.code = "201";
        this.postId = id;
        this.message = "Create new post successfully";
    }
}
