package com.olympus.service;

import com.olympus.dto.FrdReqCrt;

public interface IFriendshipService {
    boolean existsFriendship(String user1, String user2);
    Long create(String friendRequestId);
}
