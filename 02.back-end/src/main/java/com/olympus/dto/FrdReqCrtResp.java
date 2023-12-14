package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class FrdReqCrtResp {
    @Schema(example = "201")
    private String code;
    @Schema(example = "123")
    private Long requestId;
    @Schema(example = "Send friend request successfully")
    private String message;

    public FrdReqCrtResp(Long id) {
        this.code = "201";
        this.requestId = id;
        this.message = "Send friend request successfully";
    }
}
