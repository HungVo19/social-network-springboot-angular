package com.olympus.service;

import com.olympus.entity.User;

public interface IResetPwdTokenService {
    boolean existByToken(String token);

    void createToken(User user, String token);
}