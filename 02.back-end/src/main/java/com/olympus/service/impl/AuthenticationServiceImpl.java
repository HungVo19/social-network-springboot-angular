package com.olympus.service.impl;

import com.olympus.entity.Authentication;
import com.olympus.entity.User;
import com.olympus.repository.IAuthenticationRepository;
import com.olympus.service.IAuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IAuthenticationRepository authenticationRepository;

    @Autowired
    public AuthenticationServiceImpl(IAuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }
    @Override
    public boolean existsByUserIdAndCode(Authentication authentication) {
       Long userId = authentication.getUser().getId();
       String code = authentication.getCode();
       return authenticationRepository.existsByUserIdAndCode(userId,code);
    }

    @Override
    public void deleteByUser(User user) {
        authenticationRepository.deleteByUser(user);
    }

    @Override
    public void save(Authentication authentication) {
        authenticationRepository.save(authentication);
    }
}
