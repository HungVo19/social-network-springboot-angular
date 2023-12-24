package com.olympus.mapper;

import com.olympus.dto.newsfeed.PostInteractionUserDTO;
import com.olympus.dto.newsfeed.PostLikeDTO;
import com.olympus.entity.PostLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PostInteractionUserDTO.class)
public interface PostLikeDTOMapper {

    @Mapping(source = "entity.user", target = "user")
    PostLikeDTO toDTO(PostLike entity);
}
