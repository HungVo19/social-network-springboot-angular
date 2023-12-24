package com.olympus.controller;

import com.olympus.config.Constant;
import com.olympus.dto.newsfeed.NewsfeedPostDTO;
import com.olympus.dto.request.PostCreate;
import com.olympus.dto.request.PostUpdate;
import com.olympus.dto.response.BaseResponse;
import com.olympus.dto.response.CurrentUserPost;
import com.olympus.dto.response.OtherUserPost;
import com.olympus.service.IFriendshipService;
import com.olympus.service.IImageService;
import com.olympus.service.IPostService;
import com.olympus.service.IUserService;
import com.olympus.validator.AppValidator;
import com.olympus.validator.annotation.post.ExistByPostIdAndNotDeleted;
import com.olympus.validator.annotation.user.ExistUserById;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users/{userId}")
@CrossOrigin("*")
@Tag(name = "Posts", description = "User's Posts Management APIs")
@Validated
public class PostController {
    private final AppValidator appValidator;
    private final IImageService iImageService;
    private final IPostService postService;
    private final IUserService userService;
    private final IFriendshipService friendshipService;

    @Autowired
    public PostController(AppValidator appValidator,
                          IImageService iImageService,
                          IPostService postService,
                          IUserService userService,
                          IFriendshipService friendshipService) {
        this.appValidator = appValidator;
        this.iImageService = iImageService;
        this.postService = postService;
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    @GetMapping("/posts")
    @Operation(summary = "Get an user's posts")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")})
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> getUserPosts(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable @Valid @ExistUserById Long userId,
                                          @RequestParam(defaultValue = "0") @Valid int page,
                                          @RequestParam(defaultValue = "5") @Valid int size) {
        Long loggedInUserId = userService.findIdByUserDetails(userDetails);
        if (loggedInUserId.equals(userId)) {
            Page<CurrentUserPost> data = postService.getCurrentUserPosts(loggedInUserId, page, size);
            BaseResponse<Page<CurrentUserPost>, ?> response =
                    BaseResponse.success(HttpStatus.OK, Constant.MSG_OK, data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (friendshipService.existsFriendship(loggedInUserId, userId)) {
            Page<OtherUserPost> data = postService.getFriendPosts(userId, page, size);
            BaseResponse<Page<OtherUserPost>, ?> response =
                    BaseResponse.success(HttpStatus.OK, Constant.MSG_OK, data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Page<OtherUserPost> data = postService.getOtherUserPosts(userId, page, size);
        BaseResponse<Page<OtherUserPost>, ?> response =
                BaseResponse.success(HttpStatus.OK, Constant.MSG_OK, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(
            value = "/posts",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Create new post")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")})
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> createPost(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable @Valid @ExistUserById Long userId,
                                        @RequestPart @Valid PostCreate post,
                                        @RequestPart(required = false)
                                        MultipartFile[] files) throws IOException {
        ResponseEntity<?> validationError = appValidator.validatePostCreate(userDetails, userId, post, files);
        if (validationError != null) {
            return validationError;
        }

        List<String> images = uploadPostImages(files);

        Long newPostId = postService.createPost(userId, post, images);
        Map<String, Long> data = new HashMap<>();
        data.put("id", newPostId);
        BaseResponse<Map<String, Long>, ?> response =
                BaseResponse.success(HttpStatus.CREATED, Constant.MSG_SUCCESS_POST_CREATE, data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/posts/{postId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Update an existing post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")})
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> updatePost(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable @Valid @ExistUserById Long userId,
                                        @PathVariable @Valid
                                        @ExistByPostIdAndNotDeleted Long postId,
                                        @RequestPart @Valid PostUpdate post,
                                        @RequestPart(required = false) MultipartFile[] files) throws IOException {
        ResponseEntity<?> validationError = appValidator.validatePostUpdate(userDetails, userId, postId, post, files);
        if (validationError != null) {
            return validationError;
        }

        List<String> images = uploadPostImages(files);

        Long updatedPostId = postService.updatePost(postId, post, images);
        Map<String, Long> data = new HashMap<>();
        data.put("updatedPostId", updatedPostId);
        BaseResponse<Map<String, Long>, ?> response =
                BaseResponse.success(HttpStatus.CREATED, Constant.MSG_SUCCESS_POST_UPDATE, data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "Delete an existing post")
    @ApiResponses({
            @ApiResponse(responseCode = "20", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")})
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable @Valid @ExistUserById Long userId,
                                        @PathVariable @Valid @ExistByPostIdAndNotDeleted Long postId) {
        ResponseEntity<?> validationError = appValidator.validatePostDelete(userDetails, userId, postId);
        if (validationError != null) {
            return validationError;
        }
        postService.deletePost(postId);
        BaseResponse<String, ?> response =
                BaseResponse.success(HttpStatus.OK, Constant.MSG_OK, Constant.MSG_SUCCESS_POST_DELETE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/newsfeed")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = BaseResponse.class), mediaType = "application/json")})
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<?> getTimeline(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable @Valid @ExistUserById Long userId,
                                  @RequestParam(defaultValue = "0") @Valid int page,
                                  @RequestParam(defaultValue = "5") @Valid int size) {
        ResponseEntity<?> validationError = appValidator.validateNewsfeed(userDetails, userId);
        if (validationError != null) {
            return validationError;
        }
        Page<NewsfeedPostDTO> data = postService.getNewsfeed(userId, page, size);
        BaseResponse<Page<NewsfeedPostDTO>,?> response =
                BaseResponse.success(HttpStatus.OK, Constant.MSG_OK, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<String> uploadPostImages(MultipartFile[] files) throws IOException {
        List<String> images = new ArrayList<>();
        if (files != null) {
            appValidator.validateImgFile(files);
            for (MultipartFile file : files) {
                String fileName = iImageService.save(file);
                String url = iImageService.getImageUrl(fileName);
                images.add(url);
            }
        }
        return images;
    }
}
