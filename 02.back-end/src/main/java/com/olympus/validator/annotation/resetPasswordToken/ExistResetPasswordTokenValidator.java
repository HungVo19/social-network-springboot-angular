package com.olympus.validator.annotation.resetPasswordToken;

import com.olympus.service.IResetPwdTokenService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistResetPasswordTokenValidator implements ConstraintValidator<ExistResetPasswordToken, String> {
    private final IResetPwdTokenService resetPwdTokenService;

    @Autowired
    public ExistResetPasswordTokenValidator(IResetPwdTokenService resetPwdTokenService) {
        this.resetPwdTokenService = resetPwdTokenService;
    }

    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
        return !token.isBlank() && resetPwdTokenService.existByToken(token);
    }
}
