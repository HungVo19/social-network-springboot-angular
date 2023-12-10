package com.olympus.service.impl;

import com.olympus.entity.ResetPwdToken;
import com.olympus.entity.User;
import com.olympus.repository.IResetPwdTokenRepository;
import com.olympus.service.IResetPwdTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetPwdTokenImpl implements IResetPwdTokenService {
    private final IResetPwdTokenRepository resetPwdTokenRepository;

    @Autowired
    public ResetPwdTokenImpl(IResetPwdTokenRepository resetPwdTokenRepository) {
        this.resetPwdTokenRepository = resetPwdTokenRepository;
    }

    @Override
    public void deleteByUser(User user) {
        resetPwdTokenRepository.deleteByUser(user);
    }

    @Override
    public ResetPwdToken save(ResetPwdToken token) {
       return resetPwdTokenRepository.save(token);
    }
}
