package com.prodapt.license_tracker.assignment.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AssignmentExceptionHandler {

    @ExceptionHandler(AssignmentAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(
            AssignmentAlreadyExistsException ex) {

        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AssignmentLimitExceededException.class)
    public ResponseEntity<Map<String, Object>> handleLimit(
            AssignmentLimitExceededException ex) {

        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> build(
            HttpStatus status, String message) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", message);

        return new ResponseEntity<>(error, status);
    }
}
