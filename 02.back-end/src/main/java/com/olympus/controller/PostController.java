package com.olympus.controller;

import com.olympus.dto.CreatePostReq;
import com.olympus.dto.CreatePostResp;
import com.olympus.dto.ErrResp;
import com.olympus.dto.UpdateUserResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin("*")
@Tag(name = "Post", description = "User's Post Management APIs")
public class PostController {
    @PutMapping(
            value = "/{id}/posts",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(
            summary = "Create new post",
            description = "The response returns a status code and a success message. " +
                    "Otherwise return the error information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = CreatePostResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> createPost(@AuthenticationPrincipal
                                        UserDetails userDetails,
                                        @PathVariable
                                        @NotNull(message = "The Id is an invalid Id")
                                        @NotBlank(message = "The Id is an invalid Id")
                                        @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
                                        String id,
                                        @RequestPart @Valid
                                        CreatePostReq postReq,
                                        @RequestPart(required = false)
                                        MultipartFile[] files) {

    }
}
