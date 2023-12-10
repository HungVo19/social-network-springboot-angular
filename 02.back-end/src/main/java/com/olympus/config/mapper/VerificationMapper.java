package com.olympus.config.mapper;

import com.olympus.dto.VerificationDTO;
import com.olympus.entity.Authentication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VerificationMapper {
    @Mapping(source = "entity.user.id", target = "userId")
    @Mapping(source = "entity.user.email", target = "email")
    VerificationDTO toDTO(Authentication entity);
}
