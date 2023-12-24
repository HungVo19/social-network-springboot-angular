package com.olympus.mapper;

import com.olympus.dto.newsfeed.PostInteractionUserDTO;
import com.olympus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PostInteractionUserDTOMapper {
    @Mapping(source = "entity.id", target = "userId")
    @Mapping(source = "entity.email", target = "email")
    @Mapping(source = "entity", target = "displayName", qualifiedByName = "mapDisplayName")
    @Mapping(source = "entity.avatar", target = "avatarUrl")
    PostInteractionUserDTO toDTO(User entity);

    @Named("mapDisplayName")
    default String mapDisplayName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
