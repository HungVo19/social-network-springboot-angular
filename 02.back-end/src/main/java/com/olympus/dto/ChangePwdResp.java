package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class ChangePwdResp {
    @Schema(example = "200")
    private String code;
    @Schema(example = "Update password successfully")
    private String message;
    public ChangePwdResp(){
        this.code = "200";
        this.message = "Update password successfully";
    }
}
