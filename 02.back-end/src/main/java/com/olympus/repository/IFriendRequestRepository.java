package com.olympus.repository;

import com.olympus.entity.FriendRequest;
import com.olympus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    @Query("SELECT  count (f) from FriendRequest  f " +
            "where (f.sender = :user1 and  f.receiver = :user2) " +
            "or (f.receiver = :user2 and  f.sender = :user1)")
    Long existsFriendRequest(User user1, User user2);

    boolean existsBySenderOrReceiver(User user,User User);
    boolean existsByIdAndReceiver(Long requestId, User user);
}
