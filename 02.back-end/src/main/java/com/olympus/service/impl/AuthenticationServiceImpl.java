package com.olympus.service.impl;

import com.olympus.entity.Authentication;
import com.olympus.entity.User;
import com.olympus.repository.IAuthenticationRepository;
import com.olympus.service.IAuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IAuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(IAuthenticationRepository authenticationRepository,
                                     PasswordEncoder passwordEncoder) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean existsByUserIdAndCode(Authentication authentication) {
        Long userId = authentication.getUser().getId();
        String code = authentication.getCode();
        Optional<Authentication> authOptional = authenticationRepository.findByUser(authentication.getUser());
        if (authOptional.isPresent() && authenticationRepository.existsByUserIdAndCode(userId, code)) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime exprTime = authOptional.get().getCreatedTime();
            return now.isBefore(exprTime.plusHours(5));
        }
        return false;
    }

    @Override
    public void deleteByUser(User user) {
        authenticationRepository.deleteByUser(user);
    }

    @Override
    public void save(Authentication authentication) {
        authenticationRepository.save(authentication);
    }

    @Override
    public void createAuth(User user, String code) {
        String hashedCode = passwordEncoder.encode(code);
        Optional<Authentication> storedAuth = authenticationRepository.findByUser(user);
        if (storedAuth.isPresent()) {
            storedAuth.get().setCode(hashedCode);
            authenticationRepository.save(storedAuth.get());
        } else {
            Authentication authentication = new Authentication(user, hashedCode);
            authenticationRepository.save(authentication);
        }
    }
}