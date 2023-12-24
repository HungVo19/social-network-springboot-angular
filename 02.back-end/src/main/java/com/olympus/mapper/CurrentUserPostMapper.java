package com.olympus.mapper;

import com.olympus.dto.response.CurrentUserPost;
import com.olympus.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = GetPostImageMapper.class)
public interface CurrentUserPostMapper {
    @Mapping(source = "entity.id", target = "postId")
    @Mapping(source = "entity.user.id", target = "userId")
    CurrentUserPost entityToDTO(Post entity);

    List<CurrentUserPost> toListDTOs(List<Post> entities);
}
