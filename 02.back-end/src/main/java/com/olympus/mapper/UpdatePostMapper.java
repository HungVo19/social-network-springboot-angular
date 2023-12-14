package com.olympus.mapper;

import com.olympus.dto.UpdatePostReq;
import com.olympus.entity.Post;
import com.olympus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper
public interface UpdatePostMapper {
    @Mapping(source = "dto.postId", target = "entity.id")
    @Mapping(source = "dto.userId", target = "entity.user")
    @Mapping(source = "dto.content", target = "entity.content")
    @Mapping(source = "dto.privacy", target = "entity.privacy", qualifiedByName = "mapPrivacy")
    @Mapping(target = "entity.updatedTime", expression = "java(java.time.LocalDateTime.now())")
    void updateEntity(UpdatePostReq dto, @MappingTarget Post entity);

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
