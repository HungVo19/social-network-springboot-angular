package com.olympus.service.impl;

import com.olympus.dto.*;
import com.olympus.entity.Role;
import com.olympus.entity.User;
import com.olympus.mapper.UpdateUserMapper;
import com.olympus.repository.IUserRepository;
import com.olympus.service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UpdateUserMapper updateUserMapper;

    @Autowired
    UserServiceImpl(PasswordEncoder passwordEncoder,
                    IUserRepository userRepository,
                    UpdateUserMapper updateUserMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.updateUserMapper = updateUserMapper;
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
    public RegistrationResp register(RegistrationReq regUser) {
        User user = new User();
        user.setEmail(regUser.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(regUser.getPassword()));
        user.setRole(Role.ROLE_USER);
        User newUser = userRepository.save(user);
        Long newId = newUser.getId();
        return new RegistrationResp(newId);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Override
    public boolean existsUserByEmailAndPassword(LoginReq loginReq) {
        String email = loginReq.getEmail().trim().toLowerCase();
        String rawPassword = loginReq.getPassword();
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            User existedUser = user.get();
            String storedPassword = existedUser.getPassword();
            return passwordEncoder.matches(rawPassword, storedPassword);
        }
        return false;
    }

    @Override
    public void updatePwd(ChangePwdReq request) {
        String newPwd = request.getPassword();
        String email = request.getEmail().trim().toLowerCase();
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            String encryptedPwd = passwordEncoder.encode(newPwd);
            user.get().setPassword(encryptedPwd);
            userRepository.save(user.get());
        }
    }

    @Override
    public boolean existByUserId(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Long updateUser(UpdateUserReq updateUser, String imageUrl) {
        if (updateUser.getGender() != null) {
            updateUser.setGender(updateUser.getGender().toUpperCase());
        }
        if (updateUser.getStatus() != null) {
            updateUser.setStatus(updateUser.getStatus().toUpperCase());
        }
        Long id = Long.parseLong(updateUser.getId());
        User user = userRepository.findUserById(id).orElseThrow();
        if (!imageUrl.isEmpty()) {
            user.setAvatar(imageUrl);
        }
        updateUserMapper.updateEntityFromDTO(updateUser, user);
        return userRepository.save(user).getId();
    }
}
