package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema
public class RegistrationResp {
    @Schema(example = "201")
    private String code;
    @Schema(example = "123")
    private Long userId;
    @Schema(example = "Create new user successfully")
    private String message;
    public RegistrationResp(Long id) {
        this.code = "201";
        this.userId = id;
        this.message = "Create new user successfully";
    }
}
