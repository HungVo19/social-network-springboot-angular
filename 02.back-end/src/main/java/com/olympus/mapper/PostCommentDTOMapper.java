package com.olympus.mapper;

import com.olympus.dto.newsfeed.PostCommentDTO;
import com.olympus.entity.PostComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = PostInteractionUserDTOMapper.class)
public interface PostCommentDTOMapper {
    @Mapping(source = "entity.id", target = "commentId")
    @Mapping(source = "entity.user", target = "user")
    @Mapping(source = "entity.content", target = "content")
    @Mapping(source = "entity.createdTime", target = "createdTime")
    @Mapping(source = "entity.updatedTime", target = "updatedTime")
    PostCommentDTO toDTO(PostComment entity);

    List<PostCommentDTO> toListDTO(List<PostComment> listEntity);
}
