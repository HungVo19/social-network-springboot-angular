package com.olympus.mapper;

import com.olympus.dto.newsfeed.NewsfeedPostDTO;
import com.olympus.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {PostImageDTOMapper.class, PostLikeDTOMapper.class, PostCommentDTOMapper.class})
public interface NewsfeedPostDTOMapper {
    @Mapping(source = "entity.id", target = "postId")
    @Mapping(source = "entity.user.id", target = "userId")
    @Mapping(source = "entity.content", target = "content")
    @Mapping(source = "entity.createdTime", target = "createdTime")
    @Mapping(source = "entity.updatedTime", target = "updatedTime")
    @Mapping(source = "entity.images", target = "images")
    @Mapping(source = "entity.likes", target = "likes")
    @Mapping(source = "entity.comments", target = "comments")
    NewsfeedPostDTO toDTO(Post entity);

    List<NewsfeedPostDTO> toListDTO(List<Post> listEntity);
}
