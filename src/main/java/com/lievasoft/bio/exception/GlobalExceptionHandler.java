package com.lievasoft.bio.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleRepeatCustomUser(EntityExistsException ex, HttpServletRequest request) {
        var response = createErrorResponse(request.getServletPath(), ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
        String message = "username %s has not been found.".formatted(ex.getMessage());
        var response = createErrorResponse(request.getServletPath(), message);
        log.error(message, ex);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    private ErrorResponse createErrorResponse(String path, String message) {
        return new ErrorResponse(path, message, LocalDateTime.now(ZoneId.of("America/La_Paz")));
    }
}
