package com.olympus.exception;

import com.olympus.dto.ErrResp;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgsException(MethodArgumentNotValidException ex) {
        Map<String, String> message = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if(error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                message.put(fieldName, errorMessage);
            } else {
                String objectName = error.getObjectName();
                String errorMessage = error.getDefaultMessage();
                message.put(objectName,errorMessage);
            }
        });
        ErrResp errors = new ErrResp(message);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintException(ConstraintViolationException ex) {
        Map<String, String> message = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            String fieldName = ((PathImpl)constraintViolation.getPropertyPath()).getLeafNode().getName();
            String errorMessage = constraintViolation.getMessage();
            message.put(fieldName,errorMessage);
        });
        ErrResp errors = new ErrResp(message);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
