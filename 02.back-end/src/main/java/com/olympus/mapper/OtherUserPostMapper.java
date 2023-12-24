package com.olympus.mapper;

import com.olympus.dto.response.OtherUserPost;
import com.olympus.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OtherUserPostMapper {
    @Mapping(source = "entity.id", target = "postId")
    @Mapping(source = "entity.user.id", target = "userId")
    OtherUserPost entityToDTO(Post entity);

    List<OtherUserPost> toListDTOs(List<Post> entities);
}
