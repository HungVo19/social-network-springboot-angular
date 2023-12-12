package com.olympus.service.impl;

import com.google.common.hash.Hashing;
import com.olympus.entity.ResetPwdToken;
import com.olympus.entity.User;
import com.olympus.repository.IResetPwdTokenRepository;
import com.olympus.service.IResetPwdTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ResetPwdTokenImpl implements IResetPwdTokenService {
    private final IResetPwdTokenRepository resetPwdTokenRepository;

    @Autowired
    public ResetPwdTokenImpl(IResetPwdTokenRepository resetPwdTokenRepository) {
        this.resetPwdTokenRepository = resetPwdTokenRepository;
    }

    @Override
    public boolean existByToken(String token) {
        String hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();
        Optional<ResetPwdToken> resetPwdToken = resetPwdTokenRepository.findByToken(hashedToken);
        if (resetPwdToken.isPresent()) {
            String storedToken = resetPwdToken.get().getToken();
            if (storedToken.equals(hashedToken)) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime exprTime = resetPwdToken.get().getCreatedTime();
                return now.isBefore(exprTime.plusHours(5));
            }
        }
        return false;
    }

    @Override
    public void createToken(User user, String token) {
        String hashedToken = Hashing.sha256()
                .hashString(token, StandardCharsets.UTF_8)
                .toString();
        Optional<ResetPwdToken> storedToken = resetPwdTokenRepository.findByUser(user);
        if (storedToken.isPresent()) {
            storedToken.get().setToken(hashedToken);
            resetPwdTokenRepository.save(storedToken.get());
        } else {
            ResetPwdToken newToken = new ResetPwdToken(user, hashedToken);
            resetPwdTokenRepository.save(newToken);
        }
    }
}
