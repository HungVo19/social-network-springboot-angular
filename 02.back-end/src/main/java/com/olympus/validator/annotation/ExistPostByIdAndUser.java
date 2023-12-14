package com.olympus.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistPostByIdAndUserValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistPostByIdAndUser {
    String message() default "Post does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
