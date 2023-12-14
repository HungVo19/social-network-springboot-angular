package com.olympus.service.impl;

import com.olympus.dto.FrdReqCrt;
import com.olympus.entity.FriendRequest;
import com.olympus.entity.User;
import com.olympus.mapper.FrdReqCrtMapper;
import com.olympus.repository.IFriendRequestRepository;
import com.olympus.service.IFriendRequestService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FriendRequestServiceImpl implements IFriendRequestService {
    private final IFriendRequestRepository friendRequestRepository;
    private final FrdReqCrtMapper frdReqCrtMapper;

    @Autowired
    public FriendRequestServiceImpl(IFriendRequestRepository friendRequestRepository,
                                    FrdReqCrtMapper frdReqCrtMapper) {
        this.friendRequestRepository = friendRequestRepository;
        this.frdReqCrtMapper = frdReqCrtMapper;
    }

    @Override
    public boolean existsByUserId(String id1, String id2) {
        User user1 = new User(id1);
        User user2 = new User(id2);
        return friendRequestRepository.existsFriendRequest(user1, user2) != 0;
    }

    @Override
    public Long createRequest(FrdReqCrt reqCrt) {
        FriendRequest friendRequest = frdReqCrtMapper.dtoToFriendRequest(reqCrt);
        return friendRequestRepository.save(friendRequest).getId();
    }

    @Override
    public boolean isValidDeletePermission(Long userId) {
        User user = new User(userId);
        return friendRequestRepository.existsBySenderOrReceiver(user, user);
    }

    @Override
    public boolean existByRequestId(String requestId) {
        Long id = Long.valueOf(requestId);
        return friendRequestRepository.existsById(id);
    }

    @Override
    public void deleteRequest(String requestId) {
        Long id = Long.valueOf(requestId);
        friendRequestRepository.deleteById(id);
    }

    @Override
    public boolean validAccepter(Long requestId, Long userId) {
        User user = new User(userId);
        return friendRequestRepository.existsByIdAndReceiver(requestId, user);
    }

    @Override
    public FriendRequest findById(String requestId) {
        return friendRequestRepository.getReferenceById(Long.valueOf(requestId));
    }
}
