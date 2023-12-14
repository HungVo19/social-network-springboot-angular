package com.olympus.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

public @interface ExistPostId {
    @Documented
    @Constraint(validatedBy = ExistPostIdValidator.class)
    @Target({FIELD, METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExistUserId {
        String message() default "The Id does not exist";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}
