package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class FrdReqDltResp {
    @Schema(example = "200")
    private String code;
    @Schema(example = "Delete successfully")
    private String message;

    public FrdReqDltResp() {
        this.code = "200";
        this.message = "Delete request successfully";
    }
}
