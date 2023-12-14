package com.olympus.mapper;

import com.olympus.dto.GetPost;
import com.olympus.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = GetPostImageMapper.class)
public interface GetPostMapper {
    @Mapping(source = "entity.id", target = "postId")
    @Mapping(source = "entity.user.id", target = "userId")
    GetPost entityToDTO(Post entity);

    List<GetPost> entitiesToDTOs(List<Post> entities);
}
