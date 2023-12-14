package com.olympus.validator;

import com.olympus.config.Constant;
import com.olympus.dto.ErrResp;
import com.olympus.dto.FrdReqCrt;
import com.olympus.service.IFriendRequestService;
import com.olympus.service.IFriendshipService;
import com.olympus.service.IPostService;
import com.olympus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppValidator {
    private final IUserService userService;
    private final IPostService postService;
    private final IFriendRequestService friendRequestService;
    private final IFriendshipService friendshipService;

    @Autowired
    public AppValidator(IUserService userService,
                        IPostService postService,
                        IFriendRequestService friendRequestService,
                        IFriendshipService friendshipService) {
        this.userService = userService;
        this.postService = postService;
        this.friendRequestService = friendRequestService;
        this.friendshipService = friendshipService;
    }

    public ErrResp validateGetPostPermission(UserDetails userDetails, String pathUserId) {
        Long loggedInUserId = userService.findIdByUserDetails(userDetails);
        if (loggedInUserId == null
                || !loggedInUserId.equals(Long.valueOf(pathUserId))) {
            return forbiddenErr();
        }
        return null;
    }

    public ErrResp validateCreatePostPermission(UserDetails userDetails, String pathUserId, String bodyUserId) {
        Long loggedInUserId = userService.findIdByUserDetails(userDetails);
        if (loggedInUserId == null
                || !loggedInUserId.equals(Long.valueOf(pathUserId))
                || !loggedInUserId.equals(Long.valueOf(bodyUserId))) {
            return forbiddenErr();
        }
        return null;
    }

    public ErrResp validateUpdatePostPermission(UserDetails userDetails,
                                                String pathUserId,
                                                String pathPostId,
                                                String bodyUserId,
                                                String bodyPostId) {
        Long loggedInUserId = userService.findIdByUserDetails(userDetails);
        if (loggedInUserId == null
                || !loggedInUserId.equals(Long.valueOf(pathUserId))
                || !loggedInUserId.equals(Long.valueOf(bodyUserId))
                || !pathUserId.equals(bodyUserId)
                || !pathPostId.equals(bodyPostId)) {
            return forbiddenErr();
        }
        return null;
    }

    public ErrResp validateImgFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String mimeType = file.getContentType();
            boolean isImage = mimeType != null && mimeType.startsWith("image/");
            if (!isImage) {
                Map<String, String> message = new HashMap<>();
                message.put("File Error", "Invalid file format. Only image files are allowed.");
                return new ErrResp(Constant.HTTP_STATUS_CODE_400, message);
            }
        }
        return null;
    }

    public ErrResp validateImgFile(MultipartFile[] files) {
        if (files != null) {
            for (MultipartFile file : files) {
                ErrResp errRsp = validateImgFile(file);
                if (errRsp != null) {
                    return errRsp;
                }
            }
        }
        return null;
    }

    public ErrResp validateDeletePostPermission(UserDetails userDetails, String pathUserId, String pathPostId) {
        Long loggedInUserId = userService.findIdByUserDetails(userDetails);
        if (loggedInUserId == null
                || !loggedInUserId.equals(Long.valueOf(pathUserId))
                || !postService.existsByIdAndUser_Id(Long.valueOf(pathPostId), Long.valueOf(pathUserId))) {
            return forbiddenErr();
        }
        return null;
    }

    public ResponseEntity<?> validateFrdReqCrt(UserDetails userDetails, FrdReqCrt request) {
        Long loggedInUserId = userService.findIdByUserDetails(userDetails);
        if (loggedInUserId == null
                || !loggedInUserId.equals(Long.valueOf(request.getSenderId()))) {
            return createResponseEntity(forbiddenErr());
        }

        ErrResp friendRequestErr = existFriendRequestErr(request);
        if (friendRequestErr != null) {
            return createResponseEntity(friendRequestErr);
        }

        ErrResp friendshipErr = existFriendshipErr(request);
        if (friendshipErr != null) {
            return createResponseEntity(friendshipErr);
        }

        if (request.getSenderId().equals(request.getReceiverId())) {
            Map<String, String> message = new HashMap<>();
            message.put("Error", "Sender and Receiver are the same user");
            return new ResponseEntity<>(new ErrResp(Constant.HTTP_STATUS_CODE_400, message), HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public ResponseEntity<?> validateDltFrdReq(UserDetails userDetails, String requestId) {
        ResponseEntity<?> commonFriendRequestErr = validateFriendRequest(requestId);
        if (commonFriendRequestErr != null) {
            return commonFriendRequestErr;
        }

        Long loggedInUserId = userService.findIdByUserDetails(userDetails);
        if (loggedInUserId == null
                || !friendRequestService.isValidDeletePermission(loggedInUserId)) {
            return createResponseEntity(forbiddenErr());
        }
        return null;
    }

    public ResponseEntity<?> validateAcpFrdReq(UserDetails userDetails, String requestId) {
        ResponseEntity<?> commonFriendRequestErr = validateFriendRequest(requestId);
        if (commonFriendRequestErr != null) {
            return commonFriendRequestErr;
        }

        Long loggedInUserId = userService.findIdByUserDetails(userDetails);
        if (loggedInUserId == null
                || !friendRequestService.validAccepter(Long.valueOf(requestId), loggedInUserId)) {
            return createResponseEntity(forbiddenErr());
        }
        return null;
    }

    private ErrResp forbiddenErr() {
        Map<String, String> message = new HashMap<>();
        message.put("Access", "Forbidden");
        return new ErrResp(Constant.HTTP_STATUS_CODE_403, message);
    }

    private ErrResp pathIdErr(String id) {
        boolean match = id.matches("^[1-9]\\d*$");
        if (id.isBlank() || !match) {
            Map<String, String> message = new HashMap<>();
            message.put("Id", "Invalid Id");
            return new ErrResp(Constant.HTTP_STATUS_CODE_404, message);
        }
        return null;
    }

    private ErrResp existFriendRequestId(String id) {
        if (!friendRequestService.existByRequestId(id)) {
            Map<String, String> message = new HashMap<>();
            message.put("Id", "Invalid Id");
            return new ErrResp(Constant.HTTP_STATUS_CODE_404, message);
        }
        return null;
    }

    private ResponseEntity<?> createResponseEntity(ErrResp errResp) {
        String status = errResp.getStatus();
        return switch (status) {
            case Constant.HTTP_STATUS_CODE_400 -> new ResponseEntity<>(errResp, HttpStatus.BAD_REQUEST);
            case Constant.HTTP_STATUS_CODE_403 -> new ResponseEntity<>(errResp, HttpStatus.FORBIDDEN);
            case Constant.HTTP_STATUS_CODE_404 -> new ResponseEntity<>(errResp, HttpStatus.NOT_FOUND);
            default -> null;
        };
    }

    private ErrResp existFriendRequestErr(FrdReqCrt reqCrt) {
        String sender_id = reqCrt.getSenderId();
        String receiver_id = reqCrt.getReceiverId();
        if (friendRequestService.existsByUserId(sender_id, receiver_id)) {
            Map<String, String> message = new HashMap<>();
            message.put("Error", "Friend request is already sent");
            return new ErrResp(Constant.HTTP_STATUS_CODE_400, message);
        }
        return null;
    }

    private ErrResp existFriendshipErr(FrdReqCrt reqCrt) {
        String sender_id = reqCrt.getSenderId();
        String receiver_id = reqCrt.getReceiverId();
        if (friendshipService.existsFriendship(sender_id, receiver_id)) {
            Map<String, String> message = new HashMap<>();
            message.put("Error", "They are already friends");
            return new ErrResp(Constant.HTTP_STATUS_CODE_400, message);
        }
        return null;
    }

    private ResponseEntity<?> validateFriendRequest(String requestId) {
        ErrResp idError = pathIdErr(requestId);
        if (idError != null) {
            return createResponseEntity(idError);
        }

        ErrResp existRequestErr = existFriendRequestId(requestId);
        if (existRequestErr != null) {
            return createResponseEntity(existRequestErr);
        }
        return null;
    }
}
