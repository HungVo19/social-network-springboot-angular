package com.olympus.service;

import com.olympus.entity.Authentication;
import com.olympus.entity.User;

public interface IAuthenticationService {
    boolean existsByUserIdAndCode(Authentication authentication);

    void deleteByUser(User user);

    void save(Authentication authentication);

    void createAuth(User user, String code);
}
