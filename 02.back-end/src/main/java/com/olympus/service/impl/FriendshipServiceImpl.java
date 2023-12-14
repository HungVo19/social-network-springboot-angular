package com.olympus.service.impl;

import com.olympus.entity.FriendRequest;
import com.olympus.entity.Friendship;
import com.olympus.entity.User;
import com.olympus.mapper.FrdReqCrtMapper;
import com.olympus.repository.IFriendRequestRepository;
import com.olympus.repository.IFriendshipRepository;
import com.olympus.service.IFriendRequestService;
import com.olympus.service.IFriendshipService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
public class FriendshipServiceImpl implements IFriendshipService {
    private final IFriendshipRepository friendshipRepository;
    private final IFriendRequestService friendRequestService;
    @Autowired
    public FriendshipServiceImpl(IFriendshipRepository friendshipRepository,
                                 IFriendRequestService friendRequestService) {
        this.friendshipRepository = friendshipRepository;
        this.friendRequestService = friendRequestService;
    }

    @Override
    public boolean existsFriendship(String id1, String id2) {
        User user1 = new User(id1);
        User user2 = new User(id2);
        return friendshipRepository.existsFriendship(user1, user2) != 0;
    }

    @Override
    public Long create(String friendRequestId) {
        FriendRequest friendRequest = friendRequestService.findById(friendRequestId);
        Friendship friendship = new Friendship();
        friendship.setUser1(friendRequest.getSender());
        friendship.setUser2(friendRequest.getReceiver());
        friendship.setCreatedTime(LocalDate.now());
        return friendshipRepository.save(friendship).getId();
    }
}
