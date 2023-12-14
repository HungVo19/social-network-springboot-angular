package com.olympus.mapper;

import com.olympus.dto.CreatePostReq;
import com.olympus.entity.Post;
import com.olympus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface CreatePostMapper {

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "deleteStatus", constant = "false")
    @Mapping(target = "privacy", source = "privacy", qualifiedByName = "mapPrivacy")
    @Mapping(target = "createdTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedTime", expression = "java(java.time.LocalDateTime.now())")
    Post toPost(CreatePostReq createPostReq);

    default User map(String userId) {
        if (userId == null) {
            return null;
        }

        User user = new User();
        user.setId(Long.valueOf(userId));
        return user;
    }

    @Named("mapPrivacy")
    default String mapPrivacy(String privacy) {
        if (privacy == null) {
            return null;
        }
        return privacy.trim().toUpperCase();
    }
}
