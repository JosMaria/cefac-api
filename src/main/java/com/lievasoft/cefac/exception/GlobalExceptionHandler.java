package com.lievasoft.cefac.exception;

import com.lievasoft.cefac.exception.types.AlreadyExistsException;
import com.lievasoft.cefac.exception.types.OperationNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExists(AlreadyExistsException ex, HttpServletRequest request) {
        var response = createErrorResponse(request.getServletPath(), ex.getMessage(), ex.getProblem());
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleOperationNotAllowed(OperationNotAllowedException ex, HttpServletRequest request) {
        var response = createErrorResponse(request.getServletPath(), ex.getMessage(), ex.getProblem());
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    private ErrorResponse createErrorResponse(String path, String message, Problem problem) {
        return new ErrorResponse(path, message, LocalDateTime.now(ZoneId.of("America/La_Paz")), problem);
    }
//
//    @ExceptionHandler(EntityExistsException.class)
//    public ResponseEntity<ErrorResponse> handleRepeatEntity(EntityExistsException ex, HttpServletRequest request) {
//        return createErrorResponse(ex.getMessage(), request.getServletPath(), BAD_REQUEST);
//    }
//
//
//
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
//        String message = "username %s has not been found.".formatted(ex.getMessage());
//        return createErrorResponse(message, request.getServletPath(), BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ErrorResponse> handleTokenInvalidException(ExpiredJwtException ex, HttpServletRequest request) {
//        System.out.println("i am exception ExpiredJwtExceptio");
//        return createErrorResponse(ex.getMessage(), request.getServletPath(), UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult()
//                .getAllErrors()
//                .forEach(error -> {
//                    var fieldError = (FieldError) error;
//                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
//                });
//
//        log.error("Validation error in the resource {}", request.getServletPath());
//        return new ResponseEntity<>(errors, BAD_REQUEST);
//    }
//
//    private ResponseEntity<ErrorResponse> createErrorResponse(String message, String path, HttpStatus status) {
//        var response = new ErrorResponse(path, message, LocalDateTime.now(ZoneId.of("America/La_Paz")));
//        log.error(message);
//        return new ResponseEntity<>(response, status);
//    }
}
