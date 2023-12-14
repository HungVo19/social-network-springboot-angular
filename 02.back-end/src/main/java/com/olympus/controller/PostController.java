package com.olympus.controller;

import com.olympus.dto.*;
import com.olympus.service.IImageService;
import com.olympus.service.IPostService;
import com.olympus.validator.AppValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin("*")
@Tag(name = "Post", description = "User's Post Management APIs")
@Validated
public class PostController {
    private final AppValidator appValidator;
    private final IImageService iImageService;
    private final IPostService postService;

    @Autowired
    public PostController(AppValidator appValidator,
                          IImageService iImageService,
                          IPostService postService) {
        this.appValidator = appValidator;
        this.iImageService = iImageService;
        this.postService = postService;
    }

    @GetMapping("/{userId}/posts")
    @Operation(summary = "Get an user's posts")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = CreatePostResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> getUserPosts(@AuthenticationPrincipal
                                          UserDetails userDetails,
                                          @PathVariable
                                          @NotNull(message = "The Id is an invalid Id")
                                          @NotBlank(message = "The Id is an invalid Id")
                                          @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
                                          String userId) {
        ErrResp accessError = appValidator.validateGetPostPermission(userDetails, userId);
        if (accessError != null) {
            return new ResponseEntity<>(accessError, HttpStatus.FORBIDDEN);
        }
        List<GetPost> posts = postService.getAllByUserAndDeleteStatusIsFalse(Long.valueOf(userId));
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping(
            value = "/{userId}/posts",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Create new post")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = CreatePostResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
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
                                        String userId,
                                        @RequestPart @Valid
                                        CreatePostReq postReq,
                                        @RequestPart(required = false)
                                        MultipartFile[] files) throws IOException {
        ErrResp accessError =
                appValidator.validateCreatePostPermission(userDetails, userId, postReq.getUserId());
        if (accessError != null) {
            return new ResponseEntity<>(accessError, HttpStatus.FORBIDDEN);
        }

        List<String> images = new ArrayList<>();
        if (files != null) {
            ErrResp imgErrRsp = appValidator.validateImgFile(files);
            if (imgErrRsp != null) {
                return new ResponseEntity<>(imgErrRsp, HttpStatus.BAD_REQUEST);
            }
            for (MultipartFile file : files) {
                String fileName = iImageService.save(file);
                String url = iImageService.getImageUrl(fileName);
                images.add(url);
            }
        }

        Long newPostId = postService.createPost(postReq, images);
        return new ResponseEntity<>(new CreatePostResp(newPostId), HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{userId}/posts/{postId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Update an existing post")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = UpdatePostResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> updatePost(@AuthenticationPrincipal
                                        UserDetails userDetails,
                                        @PathVariable
                                        @NotNull(message = "The Id is an invalid Id")
                                        @NotBlank(message = "The Id is an invalid Id")
                                        @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
                                        @Parameter(description = "user id", example = "1")
                                        String userId,
                                        @PathVariable
                                        @NotNull(message = "The Id is an invalid Id")
                                        @NotBlank(message = "The Id is an invalid Id")
                                        @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
                                        @Parameter(description = "post id", example = "1")
                                        String postId,
                                        @RequestPart @Valid
                                        UpdatePostReq updatePostReq,
                                        @RequestPart(required = false)
                                        MultipartFile[] files) throws IOException {
        ErrResp accessError = appValidator.validateUpdatePostPermission(userDetails, userId, postId,
                updatePostReq.getUserId(), updatePostReq.getPostId());
        if (accessError != null) {
            return new ResponseEntity<>(accessError, HttpStatus.FORBIDDEN);
        }

        List<String> images = new ArrayList<>();
        if (files != null) {
            ErrResp imgErrRsp = appValidator.validateImgFile(files);
            if (imgErrRsp != null) {
                return new ResponseEntity<>(imgErrRsp, HttpStatus.BAD_REQUEST);
            }
            for (MultipartFile file : files) {
                String fileName = iImageService.save(file);
                String url = iImageService.getImageUrl(fileName);
                images.add(url);
            }
        }

        Long newPostId = postService.updatePost(updatePostReq, images);
        return new ResponseEntity<>(new CreatePostResp(newPostId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/posts/{postId}")
    @Operation(summary = "Delete an existing post")
    @ApiResponses({
            @ApiResponse(responseCode = "20",
                    content = {@Content(schema = @Schema(implementation = DeletePostResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal
                                        UserDetails userDetails,
                                        @PathVariable
                                        @NotNull(message = "The Id is an invalid Id")
                                        @NotBlank(message = "The Id is an invalid Id")
                                        @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
                                        @Parameter(description = "user id", example = "1")
                                        String userId,
                                        @NotNull(message = "The Id is an invalid Id")
                                        @NotBlank(message = "The Id is an invalid Id")
                                        @Pattern(regexp = "^[1-9]\\d*$", message = "The Id must be a positive integer")
                                        @Parameter(description = "user id", example = "1")
                                        @PathVariable String postId) {
        ErrResp accessError = appValidator.validateDeletePostPermission(userDetails, userId, postId);
        if (accessError != null) {
            return new ResponseEntity<>(accessError, HttpStatus.FORBIDDEN);
        }
        postService.deletePost(Long.valueOf(postId));
        return new ResponseEntity<>(new DeletePostResp(), HttpStatus.OK);
    }

    @GetMapping("/{userid}/timeline")
    ResponseEntity<?> getTimeline(@PathVariable String userid){

    }
}
