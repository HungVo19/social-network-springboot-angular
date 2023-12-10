package com.olympus.validator;

import com.olympus.dto.LoginRequest;
import com.olympus.service.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUserByEmailAndPassValidator implements ConstraintValidator <ValidUserByEmailAndPass, LoginRequest> {
    private final IUserService userService;

    @Autowired
    public ValidUserByEmailAndPassValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(LoginRequest loginRequest, ConstraintValidatorContext constraintValidatorContext) {
        return userService.existsUserByEmailAndPassword(loginRequest);
    }
}
