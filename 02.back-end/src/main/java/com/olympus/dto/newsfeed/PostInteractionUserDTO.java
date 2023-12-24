package com.olympus.dto.newsfeed;

import lombok.Data;

@Data
public class PostInteractionUserDTO {
    private Long userId;
    private String email;
    private String displayName;
    private String avatarUrl;
}
