package com.olympus.service;

import com.olympus.dto.*;
import com.olympus.entity.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findUserByUserId(String id);
    Optional<User> findUserByEmail(String email);
    RegistrationResp register(RegistrationReq user);
    boolean existsEmail(String email);
    boolean existsUserByEmailAndPassword(LoginReq loginReq);
    void updatePwd(ChangePwdReq request);

    Long updateUser(UpdateUserReq updateUser, String imageUrl);
    boolean existByUserId(Long id);
}
