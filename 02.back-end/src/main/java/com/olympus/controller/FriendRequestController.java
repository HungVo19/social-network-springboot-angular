package com.olympus.controller;

import com.olympus.dto.*;
import com.olympus.service.IFriendRequestService;
import com.olympus.service.IFriendshipService;
import com.olympus.validator.AppValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin("*")
@Tag(name = "Friend Request", description = "Friend Request Management APIs")
@Validated
public class FriendRequestController {
    private final AppValidator appValidator;
    private final IFriendRequestService friendRequestService;
    private final IFriendshipService friendshipService;

    @Autowired
    public FriendRequestController(AppValidator appValidator,
                                   IFriendRequestService friendRequestService,
                                   IFriendshipService friendshipService) {
        this.appValidator = appValidator;
        this.friendRequestService = friendRequestService;
        this.friendshipService = friendshipService;
    }

    @PostMapping("/v1/friend-request")
    @Operation(summary = "Send a friend request")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = FrdReqCrtResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> createFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid FrdReqCrt request) {
        ResponseEntity<?> validationError = appValidator.validateFrdReqCrt(userDetails, request);
        if (validationError != null) {
            return validationError;
        }

        Long requestId = friendRequestService.createRequest(request);
        return new ResponseEntity<>(new FrdReqCrtResp(requestId), HttpStatus.OK);
    }

    @DeleteMapping("/v1/friend-request/{requestId}")
    @Operation(summary = "Cancel a friend request")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = FrdReqDltResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> deleteFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable
            String requestId) {
        ResponseEntity<?> validationError = appValidator.validateDltFrdReq(userDetails, requestId);
        if (validationError != null) {
            return validationError;
        }
        friendRequestService.deleteRequest(requestId);
        return new ResponseEntity<>(new FrdReqDltResp(), HttpStatus.OK);
    }

    @PutMapping("/v1/friend-request/{requestId}")
    @Operation(summary = "Confirm a friend request")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = FrdReqAcpResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",
                    content = {@Content(schema = @Schema(implementation = ErrResp.class),
                            mediaType = "application/json")}),
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<?> acceptFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String requestId) {
        ResponseEntity<?> validationError = appValidator.validateAcpFrdReq(userDetails, requestId);
        if (validationError != null) {
            return validationError;
        }
        friendshipService.create(requestId);
        friendRequestService.deleteRequest(requestId);
        return null;
    }
}
