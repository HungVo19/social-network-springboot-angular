package com.olympus.repository;

import com.olympus.entity.ResetPwdToken;
import com.olympus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResetPwdTokenRepository extends JpaRepository<ResetPwdToken, Long> {
    void deleteByUser(User user);
}
