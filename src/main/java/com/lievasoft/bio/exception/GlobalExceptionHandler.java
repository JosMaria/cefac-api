package com.lievasoft.bio.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityExistsException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleRepeatCustomUser(PersistenceException ex, HttpServletRequest request) {
        return createErrorResponse(ex.getMessage(), request.getServletPath(), BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
        String message = "username %s has not been found.".formatted(ex.getMessage());
        return createErrorResponse(message, request.getServletPath(), BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleTokenInvalidException(ExpiredJwtException ex, HttpServletRequest request) {
        return createErrorResponse(ex.getMessage(), request.getServletPath(), UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    var fieldError = (FieldError) error;
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                });

        log.error("Validation error in the resource {}", request.getServletPath());
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String message, String path, HttpStatus status) {
        var response = new ErrorResponse(path, message, LocalDateTime.now(ZoneId.of("America/La_Paz")));
        log.error(message);
        return new ResponseEntity<>(response, status);
    }
}
