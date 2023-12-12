package com.olympus.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidUserByEmailAndPassValidator.class)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserByEmailAndPass {
    String message() default "Invalid email or password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
