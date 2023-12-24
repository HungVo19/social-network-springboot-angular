package com.olympus.repository;

import com.olympus.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    boolean existsByIdAndUser_Id(Long postId, Long userId);

    Page<Post> getAllByUser_IdAndDeleteStatusIsFalseOrderByCreatedTimeDesc(Long userId, Pageable pageable);

    @Query("SELECT p from Post p " +
            "where p.user.id in :friendIds and p.deleteStatus = false  and p.privacy = 'FRIENDS' " +
            "order by p.createdTime desc ")
    Page<Post> findPostByFriendsAndDeleteStatusAndPrivacy(List<Long> friendIds, Pageable pageable);

    @Query("select count(p) from Post  p where p.user.id = :userId and p.createdTime > :lastWeek and p.deleteStatus = false")
    Integer countTotalPostLastWeek(Long userId, LocalDateTime lastWeek);

    @Query("select p.id from Post  p where p.user.id = :userId and p.deleteStatus = false")
    List<Long> findListPostIdsOfUser(Long userId);

    @Query("select count (p) from Post  p where p.id = :postId and p.deleteStatus = false")
    Long existByIdAndNotDeleted(Long postId);

    @Query("select p from Post p where p.user.id = :userId and (p.privacy  ='FRIENDS' or p.privacy = 'PUBLIC') and p.deleteStatus = false order by p.createdTime")
    Page<Post> findFriendUserPost(Long userId, Pageable pageable);

    @Query("select p from Post p where p.user.id = :userId and p.privacy = 'PUBLIC' and p.deleteStatus = false order by p.createdTime")
    Page<Post> findOtherUserPost(Long userId, Pageable pageable);
}
