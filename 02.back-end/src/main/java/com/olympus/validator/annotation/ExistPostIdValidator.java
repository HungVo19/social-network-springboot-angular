package com.olympus.validator.annotation;

import com.olympus.service.IPostService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistPostIdValidator implements ConstraintValidator<ExistPostId, String> {
    private final IPostService postService;

    @Autowired
    public ExistPostIdValidator(IPostService postService) {
        this.postService = postService;
    }

    @Override
    public void initialize(ExistPostId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return postService.existByPostId(Long.valueOf(id));
        } catch (Exception e) {
            return false;
        }
    }
}
