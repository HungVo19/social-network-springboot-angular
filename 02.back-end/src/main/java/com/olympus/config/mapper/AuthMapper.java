package com.olympus.config.mapper;

import com.olympus.dto.AuthRequest;
import com.olympus.entity.Authentication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthMapper {
    @Mapping(target = "email", source = "entity.user.email")
    @Mapping(target = "code", source = "entity.code")
    AuthRequest toAuthRequest(Authentication entity);
}
