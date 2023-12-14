package com.olympus.service;

import com.olympus.dto.FrdReqCrt;
import com.olympus.entity.FriendRequest;

public interface IFriendRequestService {
    boolean existsByUserId(String id1, String id2);
    Long createRequest(FrdReqCrt reqCrt);
    boolean isValidDeletePermission(Long userId);
    void deleteRequest(String requestId);
    boolean existByRequestId(String requestId);
    boolean validAccepter(Long requestId, Long userId);
    FriendRequest findById(String requestId);
}
