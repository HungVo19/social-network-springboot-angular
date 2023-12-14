package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FrdReqAcpResp {
    @Schema(example = "201")
    private String code;
    @Schema(example = "123")
    private Long requestId;
    @Schema(example = "Friend request accepted")
    private String message;

    public FrdReqAcpResp(Long id) {
        this.code = "201";
        this.requestId = id;
        this.message = "Friend request accepted";
    }
}
