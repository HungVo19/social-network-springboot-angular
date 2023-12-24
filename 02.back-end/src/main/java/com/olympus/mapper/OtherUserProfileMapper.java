package com.olympus.mapper;

import com.olympus.dto.response.OtherUserProfile;
import com.olympus.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface OtherUserProfileMapper {
    OtherUserProfile toDTO(User user);
}
