package com.olympus.service.impl;

import com.olympus.dto.LoginRequest;
import com.olympus.dto.RegistrationRequest;
import com.olympus.dto.RegistrationResponse;
import com.olympus.entity.Role;
import com.olympus.entity.User;
import com.olympus.repository.IUserRepository;
import com.olympus.service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(PasswordEncoder passwordEncoder,
                    IUserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByUserId(String id) {
        return userRepository.findUserById(Long.valueOf(id));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public RegistrationResponse register(RegistrationRequest regUser) {
        User user = new User();
        user.setEmail(regUser.getEmail());
        user.setPassword(passwordEncoder.encode(regUser.getPassword()));
        user.setRole(new Role(3L));
        User newUser = userRepository.save(user);
        Long newId = newUser.getId();
        return new RegistrationResponse(newId);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsUserByEmailAndPassword(LoginRequest loginRequest) {
        String email = loginRequest.getEmail().trim().toLowerCase();
        String rawPassword = loginRequest.getPassword();
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            User existedUser = user.get();
            String storedPassword = existedUser.getPassword();
            return passwordEncoder.matches(rawPassword, storedPassword);
        }
        return false;
    }
}
