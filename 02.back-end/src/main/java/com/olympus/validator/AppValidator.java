package com.olympus.validator;

import com.olympus.dto.ErrResp;
import com.olympus.entity.User;
import com.olympus.service.IImageService;
import com.olympus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class AppValidator {
    private final IUserService userService;
    private final IImageService iImageService;

    @Autowired
    public AppValidator(IUserService userService,
                        IImageService iImageService) {
        this.userService = userService;
        this.iImageService = iImageService;
    }

    public ResponseEntity<?> checkUserPermission(UserDetails userDetails, Long pathId, Long accessId) {
        String email = userDetails.getUsername();
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isEmpty()
                || !pathId.equals(user.get().getId())
                || !Objects.equals(user.get().getId(), accessId)) {
            Map<String, String> message = new HashMap<>();
            message.put("Access", "Forbidden");
            ErrResp errResp = new ErrResp("403", message);
            return new ResponseEntity<>(errResp, HttpStatus.FORBIDDEN);
        }
        return null;
    }

    public ResponseEntity<?> validateImgFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String mimeType = file.getContentType();
            boolean isImage = mimeType != null && mimeType.startsWith("image/");
            if (!isImage) {
                Map<String, String> message = new HashMap<>();
                message.put("File Error", "Invalid file format. Only image files are allowed.");
                ErrResp errResp = new ErrResp("400", message);
                return new ResponseEntity<>(errResp, HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }
}
