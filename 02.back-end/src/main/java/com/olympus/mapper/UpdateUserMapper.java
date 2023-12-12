package com.olympus.mapper;

import com.olympus.dto.UpdateUserReq;
import com.olympus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateUserMapper {
    @Mapping(source = "dto.firstName", target = "entity.firstName")
    @Mapping(source = "dto.lastName", target = "entity.lastName")
    @Mapping(source = "dto.birthDate", target = "entity.birthDate")
    @Mapping(source = "dto.phoneNumber", target = "entity.phoneNumber")
    @Mapping(source = "dto.currentAddress", target = "entity.currentAddress")
    @Mapping(source = "dto.occupation", target = "entity.occupation")
    @Mapping(source = "dto.gender", target = "entity.gender")
    @Mapping(source = "dto.status", target = "entity.status")
    void updateEntityFromDTO(UpdateUserReq dto, @MappingTarget User entity);
}
