package com.olympus.repository;

import com.olympus.entity.Friendship;
import com.olympus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFriendshipRepository extends JpaRepository<Friendship, Long> {
    @Query("SELECT  count (f) from Friendship  f " +
            "where (f.user1 = :user1 and  f.user2 = :user2) " +
            "or (f.user1 = :user2 and  f.user2 = :user1)")
    Long existsFriendship(User user1, User user2);
}
