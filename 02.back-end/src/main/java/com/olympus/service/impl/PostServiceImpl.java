package com.olympus.service.impl;

import com.olympus.dto.CreatePostReq;
import com.olympus.dto.GetPost;
import com.olympus.dto.UpdatePostReq;
import com.olympus.entity.Post;
import com.olympus.entity.User;
import com.olympus.mapper.CreatePostMapper;
import com.olympus.mapper.GetPostMapper;
import com.olympus.mapper.UpdatePostMapper;
import com.olympus.repository.IPostRepository;
import com.olympus.service.IPostImageService;
import com.olympus.service.IPostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final CreatePostMapper createPostMapper;
    private final IPostImageService postImageService;
    private final UpdatePostMapper updatePostMapper;
    private final GetPostMapper getPostMapper;

    @Autowired
    public PostServiceImpl(IPostRepository postRepository,
                           CreatePostMapper createPostMapper,
                           IPostImageService postImageService,
                           UpdatePostMapper updatePostMapper,
                           GetPostMapper getPostMapper) {
        this.postRepository = postRepository;
        this.createPostMapper = createPostMapper;
        this.postImageService = postImageService;
        this.updatePostMapper = updatePostMapper;
        this.getPostMapper = getPostMapper;
    }

    @Override
    public List<GetPost> getAllByUserAndDeleteStatusIsFalse(Long userId) {
        User user = new User(userId);
        List<Post> posts = postRepository.getAllByUserAndDeleteStatusIsFalse(user);
        return getPostMapper.entitiesToDTOs(posts);
    }

    @Override
    public Long createPost(CreatePostReq createPostReq, List<String> imageUrls) {
        Post postReq = createPostMapper.toPost(createPostReq);
        Post newPost = postRepository.save(postReq);

        if (!imageUrls.isEmpty()) {
            postImageService.save(imageUrls, newPost);
        }

        return newPost.getId();
    }

    @Override
    public Long updatePost(UpdatePostReq updatePostReq, List<String> imageUrls) {
        Long accessId = Long.valueOf(updatePostReq.getPostId());
        Post currentPost = postRepository.findById(accessId).orElseThrow();
        updatePostMapper.updateEntity(updatePostReq, currentPost);

        postImageService.deleteByPost(currentPost);

        if (!imageUrls.isEmpty()) {
            postImageService.save(imageUrls, currentPost);
        }
        return currentPost.getId();
    }

    @Override
    public boolean existByPostId(Long id) {
        return postRepository.existsById(id);
    }

    @Override
    public boolean existsByIdAndUser_Id(Long postId, Long userId) {
        return postRepository.existsByIdAndUser_Id(postId, userId);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.getReferenceById(id);
        post.setDeleteStatus(true);
        postRepository.save(post);
    }
}
