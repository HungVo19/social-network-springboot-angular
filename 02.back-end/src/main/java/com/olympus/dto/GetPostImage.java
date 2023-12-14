package com.olympus.dto;

import lombok.Data;

@Data
public class GetPostImage {
    private String imageId;
    private String url;
    private String postId;
}
