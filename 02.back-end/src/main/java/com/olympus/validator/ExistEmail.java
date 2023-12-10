package com.olympus.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistEmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistEmail {
    String message() default  "The email does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
