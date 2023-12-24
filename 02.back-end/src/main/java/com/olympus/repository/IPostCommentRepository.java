package com.olympus.repository;

import com.olympus.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPostCommentRepository extends JpaRepository<PostComment,Long> {
    @Query("select count(c) from PostComment c where c.post.id IN :listPostIds and c.createdTime > :lastWeek and c.deleteStatus = false")
    Integer countNewPostCommentLastWeek(List<Long> listPostIds, LocalDateTime lastWeek);

    @Query("select count(c) from PostComment c where c.id = :commentId and c.deleteStatus = false ")
    Long existByIdAndNotDeleted(Long commentId);

    @Query("select count(c) from  PostComment  c where c.id = :commentId and c.post.id = :postId and c.user.id = :commenterId and c.deleteStatus = false ")
    Long checkOriginalCommenter(Long commentId, Long postId, Long commenterId);
}
