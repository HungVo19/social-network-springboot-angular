package com.olympus.service;

import com.olympus.entity.ResetPwdToken;
import com.olympus.entity.User;

import java.util.Optional;

public interface IResetPwdTokenService {
    ResetPwdToken save(ResetPwdToken token);
    void deleteByUser(User user);
}
