package com.olympus.service;

import com.olympus.dto.CreatePostReq;
import com.olympus.dto.GetPost;
import com.olympus.dto.UpdatePostReq;

import java.util.List;

public interface IPostService {
    Long createPost(CreatePostReq createPostReq, List<String> imageUrls);

    Long updatePost(UpdatePostReq createPostReq, List<String> imageUrls);

    boolean existByPostId(Long id);

    boolean existsByIdAndUser_Id(Long postId, Long userId);

    void deletePost(Long id);

    List<GetPost> getAllByUserAndDeleteStatusIsFalse(Long userId);
}
