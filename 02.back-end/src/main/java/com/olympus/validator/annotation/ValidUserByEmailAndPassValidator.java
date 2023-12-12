package com.olympus.validator.annotation;

import com.olympus.dto.LoginReq;
import com.olympus.service.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUserByEmailAndPassValidator implements ConstraintValidator <ValidUserByEmailAndPass, LoginReq> {
    private final IUserService userService;

    @Autowired
    public ValidUserByEmailAndPassValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(LoginReq loginReq, ConstraintValidatorContext constraintValidatorContext) {
        return userService.existsUserByEmailAndPassword(loginReq);
    }
}
