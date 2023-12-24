package com.olympus.dto.newsfeed;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NewsfeedPostDTO {
    private Long postId;
    private Long userId;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;
    private List<PostImageDTO> images;
    private List<PostLikeDTO> likes;
    private List<PostCommentDTO> comments;
}
