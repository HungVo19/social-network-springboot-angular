package com.olympus.repository;

import com.olympus.entity.Post;
import com.olympus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    boolean existsById(Long id);

    boolean existsByIdAndUser_Id(Long postId, Long userId);

    List<Post> getAllByUserAndDeleteStatusIsFalse(User user);
}
