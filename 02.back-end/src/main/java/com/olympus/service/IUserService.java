package com.olympus.service;

import com.olympus.dto.LoginRequest;
import com.olympus.dto.RegistrationRequest;
import com.olympus.dto.RegistrationResponse;
import com.olympus.entity.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findUserByUserId(String id);
    Optional<User> findUserByEmail(String email);
    RegistrationResponse register(RegistrationRequest user);
    boolean existsEmail(String email);
    boolean existsUserByEmailAndPassword(LoginRequest loginRequest);
}
