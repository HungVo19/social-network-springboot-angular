package com.olympus.service.impl;

import com.olympus.dto.request.PostCreate;
import com.olympus.dto.request.PostUpdate;
import com.olympus.dto.response.OtherUserPost;
import com.olympus.dto.response.curentUserPost.CurrentUserPost;
import com.olympus.dto.response.newsfeed.NewsfeedPostDTO;
import com.olympus.entity.Post;
import com.olympus.entity.User;
import com.olympus.mapper.*;
import com.olympus.repository.IPostRepository;
import com.olympus.service.IFriendshipService;
import com.olympus.service.IPostImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private IPostRepository postRepository;
    @Mock
    private PostCreateMapper postCreateMapper;
    @Mock
    private IPostImageService postImageService;
    @Mock
    private PostUpdateMapper postUpdateMapper;
    @Mock
    private CurrentUserPostMapper currentUserPostMapper;
    @Mock
    private IFriendshipService friendshipService;
    @Mock
    private NewsfeedPostMapper newsfeedPostMapper;
    @Mock
    private OtherUserPostMapper otherUserPostMapper;

    @Test
    public void testCreatePost() {
        // Arrange
        Long userId = 1L;
        List<String> imageUrls = List.of("https://example.com/image1.jpg");
        PostCreate postCreate = new PostCreate();
        Post newPost = new Post();
        newPost.setId(1L); // simulate the saved post with an ID

        when(postCreateMapper.toPost(postCreate)).thenReturn(newPost);
        when(postRepository.save(any(Post.class))).thenReturn(newPost);

        // Act
        Long newPostId = postService.createPost(userId, postCreate, imageUrls);

        // Assert
        assertNotNull(newPostId);
        // Verify that postImageService.save was called
        verify(postImageService).save(eq(imageUrls), any(Post.class));
    }

    @Test
    public void testUpdatePost() {
        // Arrange
        Long postId = 1L;
        List<String> imageUrls = List.of("https://example.com/image1.jpg");
        PostUpdate postUpdate = new PostUpdate();
        Post post = new Post();
        post.setId(postId); // Set an ID to simulate the retrieved entity

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act
        Long updatedPostId = postService.updatePost(postId, postUpdate, imageUrls);

        // Assert
        assertEquals(postId, updatedPostId);
        // Verify that deleteByPost was called
        verify(postImageService).deleteByPost(any(Post.class));
        // Verify that save was called with the new images
        verify(postImageService).save(eq(imageUrls), any(Post.class));
    }

    @Test
    public void testExistByPostIdAndNotDeleted_Exists() {
        // Arrange
        Long id = 1L;
        when(postRepository.existByIdAndNotDeleted(id)).thenReturn(1L);

        // Act
        boolean exists = postService.existByPostIdAndNotDeleted(id);

        // Assert
        assertTrue(exists);
    }

    @Test
    public void testExistByPostIdAndNotDeleted_NotExists() {
        // Arrange
        Long id = 1L;
        when(postRepository.existByIdAndNotDeleted(id)).thenReturn(0L);

        // Act
        boolean exists = postService.existByPostIdAndNotDeleted(id);

        // Assert
        assertFalse(exists);
    }

    @Test
    public void testExistsByIdAndUserId_Exists() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;
        when(postRepository.existsByIdAndUser_Id(postId, userId)).thenReturn(true);

        // Act
        boolean exists = postService.existsByIdAndUser_Id(postId, userId);

        // Assert
        assertTrue(exists);
    }

    @Test
    public void testExistsByIdAndUserId_NotExists() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;
        when(postRepository.existsByIdAndUser_Id(postId, userId)).thenReturn(false);

        // Act
        boolean exists = postService.existsByIdAndUser_Id(postId, userId);

        // Assert
        assertFalse(exists);
    }


    @Test
    public void testDeletePost() {
        // Arrange
        Long id = 1L;
        Post post = new Post(); // Assume this is a valid Post object
        when(postRepository.getReferenceById(id)).thenReturn(post);

        // Act
        postService.deletePost(id);

        // Assert
        assertTrue(post.isDeleteStatus());
        // Verify that the post is saved with updated status
        verify(postRepository).save(post);
    }

    @Test
    public void testGetNewsfeed() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));
        List<Long> friendIds = List.of(2L, 3L); // Assuming these are friend IDs

        when(friendshipService.getListFriendIds(userId)).thenReturn(friendIds);

        List<Post> mockPosts = new ArrayList<>(); // Create an empty list or a list with mock posts as needed
        Page<Post> mockPage = new PageImpl<>(mockPosts, pageable, 0);
        when(postRepository.findPostByFriendsAndDeleteStatusAndPrivacy(friendIds, pageable))
                .thenReturn(mockPage); // Simulate empty posts page

        // Act
        Page<NewsfeedPostDTO> newsfeed = postService.getNewsfeed(userId, page, size);

        // Assert
        assertNotNull(newsfeed);
        assertTrue(newsfeed.isEmpty());
    }

    @Test
    public void testGetCurrentUserPosts() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        when(postRepository.getCurrentUserPosts(userId, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0)); // Simulate empty posts page

        // Act
        Page<CurrentUserPost> userPosts = postService.getCurrentUserPosts(userId, page, size);

        // Assert
        assertNotNull(userPosts);
        assertTrue(userPosts.isEmpty());
    }

    @Test
    public void testGetFriendPosts() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));

        when(postRepository.findFriendUserPost(userId, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0)); // Simulate empty posts page

        // Act
        Page<OtherUserPost> friendPosts = postService.getFriendPosts(userId, page, size);

        // Assert
        assertNotNull(friendPosts);
        assertTrue(friendPosts.isEmpty());
    }

    @Test
    public void testGetOtherUserPosts() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        when(postRepository.findOtherUserPost(userId, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0)); // Simulate empty posts page

        // Act
        Page<OtherUserPost> otherUserPosts = postService.getOtherUserPosts(userId, page, size);

        // Assert
        assertNotNull(otherUserPosts);
        assertTrue(otherUserPosts.isEmpty());
    }

    @Test
    public void testGetCurrentUserSpecificPost() {
        // Arrange
        Long userId = 1L;
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setUser(new User(userId));
        when(postRepository.getSpecificPost(userId, postId)).thenReturn(post);

        CurrentUserPost expectedCurrentUserPost = new CurrentUserPost(); // Assuming this is your DTO
        // Mocking the mapper to return a valid DTO
        when(currentUserPostMapper.toDTO(post)).thenReturn(expectedCurrentUserPost);

        // Act
        CurrentUserPost currentUserPost = postService.getCurrentUserSpecificPost(userId, postId);

        // Assert
        assertNotNull(currentUserPost);
    }

    @Test
    public void testGetFriendSpecificPost() {
        // Arrange
        Long userId = 1L;
        Long postId = 1L;
        Post post = new Post(); // Assume this is a valid Post object
        when(postRepository.getSpecificPost(userId, postId)).thenReturn(post);

        OtherUserPost otherUserPost = new OtherUserPost();
        // Mocking the mapper to return a valid DTO
        when(otherUserPostMapper.toDTO(post)).thenReturn(otherUserPost);

        // Act
        OtherUserPost friendSpecificPost = postService.getFriendSpecificPost(userId, postId);

        // Assert
        assertNotNull(otherUserPost);
    }

    @Test
    public void testGetOtherUserSpecificPost() {
        // Arrange
        Long userId = 1L;
        Long postId = 1L;
        Post post = new Post(); // Assume this is a valid Post object
        when(postRepository.getSpecificPost(userId, postId)).thenReturn(post);

        OtherUserPost otherUserPost = new OtherUserPost();
        // Mocking the mapper to return a valid DTO
        when(otherUserPostMapper.toDTO(post)).thenReturn(otherUserPost);

        // Act
        OtherUserPost otherUserSpecificPost = postService.getOtherUserSpecificPost(userId, postId);

        // Assert
        assertNotNull(otherUserPost);
    }
}