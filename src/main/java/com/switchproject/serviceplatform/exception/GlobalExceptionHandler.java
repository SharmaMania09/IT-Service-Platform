package com.switchproject.serviceplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
    @ExceptionHandler(ServiceRequestNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ServiceRequestNotFoundException exception)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException exception)
    {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ValidationErrorResponse response = new ValidationErrorResponse(400, "Validation Failed", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ValidationErrorResponse> handleStatusErrors(IllegalArgumentException exception)
    {
        Map<String, String> errors = new HashMap<>();

        errors.put("status", exception.getMessage());
        
        ValidationErrorResponse response = new ValidationErrorResponse(400, "Invalid Status", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserAlreadyPresentException.class)
    public ResponseEntity<String> handleUserAlreadyPresentErrors(UserAlreadyPresentException exception)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid value for parameter: " + exception.getName());
    }
}
