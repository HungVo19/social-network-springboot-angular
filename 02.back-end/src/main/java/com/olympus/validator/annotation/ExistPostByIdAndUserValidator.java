package com.olympus.validator.annotation;

import com.olympus.dto.UpdatePostReq;
import com.olympus.service.IPostService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistPostByIdAndUserValidator implements ConstraintValidator<ExistPostByIdAndUser, UpdatePostReq> {
    private final IPostService postService;

    @Autowired
    public ExistPostByIdAndUserValidator(IPostService postService) {
        this.postService = postService;
    }

    @Override
    public boolean isValid(UpdatePostReq updatePostReq, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Long postId = Long.valueOf(updatePostReq.getPostId());
            Long userId = Long.valueOf(updatePostReq.getUserId());
            return postService.existsByIdAndUser_Id(postId, userId);
        } catch (Exception e) {
            return false;
        }
    }
}
