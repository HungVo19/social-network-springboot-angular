package com.olympus.validator.annotation;

import com.olympus.service.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistUserIdValidator implements ConstraintValidator<ExistUserId, String> {
    private final IUserService userService;

    @Autowired
    public ExistUserIdValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return userService.existByUserId(Long.valueOf(id));
        } catch (Exception e){
            return false;
        }
    }
}
