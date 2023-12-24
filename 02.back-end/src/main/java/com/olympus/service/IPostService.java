package com.olympus.service;

import com.olympus.dto.newsfeed.NewsfeedPostDTO;
import com.olympus.dto.request.PostCreate;
import com.olympus.dto.request.PostUpdate;
import com.olympus.dto.response.CurrentUserPost;
import com.olympus.dto.response.OtherUserPost;
import com.olympus.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPostService {
    Long createPost(Long userId, PostCreate postCreate, List<String> imageUrls);
    Long updatePost(Long postId, PostUpdate createPostReq, List<String> imageUrls);
    boolean existByPostIdAndNotDeleted(Long id);
    boolean existsByIdAndUser_Id(Long postId, Long userId);
    void deletePost(Long id);
    Post findByPostId(long id);
    Page<NewsfeedPostDTO> getNewsfeed(Long userId, int page, int size);
    Page<CurrentUserPost> getCurrentUserPosts(Long loggedInUserId, int page, int size);
    Page<OtherUserPost> getFriendPosts(Long userId, int page, int size);
    Page<OtherUserPost> getOtherUserPosts(Long userId, int page, int size);
}
