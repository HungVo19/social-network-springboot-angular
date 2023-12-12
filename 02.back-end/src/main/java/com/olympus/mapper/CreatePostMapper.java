package com.olympus.mapper;

import com.olympus.dto.CreatePostReq;
import com.olympus.entity.Post;
import com.olympus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreatePostMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "deleteStatus", constant = "false")
    @Mapping(target = "privacy", source = "privacy")
    @Mapping(target = "createdTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "images", ignore = true)
    Post toPost(CreatePostReq createPostReq);

    default User map(String userId) {
        if (userId == null) {
            return null;
        }

        User user = new User();
        user.setId(Long.valueOf(userId));
        return user;
    }
}
