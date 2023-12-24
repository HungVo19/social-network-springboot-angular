package com.olympus.mapper;

import com.olympus.dto.request.UserUpdate;
import com.olympus.entity.Gender;
import com.olympus.entity.MaritalStatus;
import com.olympus.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateMapper {
    @Mapping(source = "dto.firstName", target = "entity.firstName")
    @Mapping(source = "dto.lastName", target = "entity.lastName")
    @Mapping(source = "dto.birthDate", target = "entity.birthDate")
    @Mapping(source = "dto.phoneNumber", target = "entity.phoneNumber")
    @Mapping(source = "dto.currentAddress", target = "entity.currentAddress")
    @Mapping(source = "dto.occupation", target = "entity.occupation")
    @Mapping(source = "dto", target = "entity.gender", qualifiedByName = "mapGender")
    @Mapping(source = "dto", target = "entity.status", qualifiedByName = "mapStatus")
    void updateEntityFromDTO(UserUpdate dto, @MappingTarget User entity);

    @Named("mapGender")
    default Gender mapGender(UserUpdate dto) {
        if (dto.getGender() != null) {
            return Gender.valueOf(dto.getGender().toUpperCase());
        }
        return null;
    }

    @Named("mapStatus")
    default MaritalStatus mapStatus(UserUpdate dto) {
        if (dto.getStatus() != null) {
            return MaritalStatus.valueOf(dto.getStatus().toUpperCase());
        }
        return null;
    }
}
