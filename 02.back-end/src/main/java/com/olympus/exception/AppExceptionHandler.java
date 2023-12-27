package com.olympus.exception;

import com.olympus.config.Constant;
import com.olympus.dto.response.BaseResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        BaseResponse<String, ?> error =
                BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, Constant.MSG_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgsException(MethodArgumentNotValidException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errorDetails.put(fieldName, errorMessage);
            } else {
                String objectName = error.getObjectName();
                String errorMessage = error.getDefaultMessage();
                errorDetails.put(objectName, errorMessage);
            }
        });
        BaseResponse<Map<String, String>, ?> error = BaseResponse.error(HttpStatus.BAD_REQUEST, Constant.MSG_ERROR,
                MethodArgumentNotValidException.class.getSimpleName(), errorDetails);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintException(ConstraintViolationException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            String fieldName = ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().getName();
            String errorMessage = constraintViolation.getMessage();
            errorDetails.put(fieldName, errorMessage);
        });
        BaseResponse<Map<String, String>, ?> error = BaseResponse.error(HttpStatus.BAD_REQUEST, Constant.MSG_ERROR,
                MethodArgumentNotValidException.class.getSimpleName(), errorDetails);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleMessagingException(MessagingException ex) {
        BaseResponse<String, ?> error =
                BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, Constant.MSG_ERROR,
                        MessagingException.class.getSimpleName(), ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException ex) {
        BaseResponse<String, ?> error =
                BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, Constant.MSG_ERROR,
                        IOException.class.getSimpleName(), ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        BaseResponse<String, ?> error =
                BaseResponse.error(HttpStatus.BAD_REQUEST, Constant.MSG_ERROR,
                        UserNotFoundException.class.getSimpleName(), ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<?> handleInvalidImageException() {
        Map<String, String> error = new HashMap<>();
        error.put("File Error", "Invalid file format. Only image files are allowed.");
        BaseResponse<Map<String, String>, ?> response =
                BaseResponse.error(HttpStatus.BAD_REQUEST, Constant.MSG_ERROR,
                        Constant.ERR_IMAGE_INVALID, error);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReportCreateException.class)
    public ResponseEntity<?> handleReportCreateException(ReportCreateException ex) {
        String error = ex.getDetail();
        BaseResponse<String, ?> response =
                BaseResponse.error(HttpStatus.BAD_REQUEST, Constant.MSG_ERROR,
                        HttpStatus.CONFLICT.name(), error);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
