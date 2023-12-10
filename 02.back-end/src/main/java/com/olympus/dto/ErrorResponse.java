package com.olympus.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Schema
public class ErrorResponse {
    @Schema(example = "400")
    private String status;
    @Schema(
            type = "array",
            example = " {\"field1\" :\"error info\" ,"
                    + "\"field2\": \"error info\"}")
    private Map<String, String> message;

    public ErrorResponse(Map<String, String> message) {
        this.status = "400";
        this.message = message;
    }
}
