package com.olympus.mapper;

import com.olympus.dto.FrdReqCrt;
import com.olympus.entity.FriendRequest;
import com.olympus.entity.Friendship;
import com.olympus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface FrdReqCrtMapper {
    @Mapping(source = "dto.senderId", target = "sender", qualifiedByName = "mapUser")
    @Mapping(source = "dto.receiverId", target = "receiver", qualifiedByName = "mapUser")
    FriendRequest dtoToFriendRequest(FrdReqCrt dto);

    @Named("mapUser")
    default User mapUser(String userId){
        if (userId == null) {
            return null;
        }
        return new User(userId);
    }
}
