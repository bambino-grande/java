package ru.pogosian.presentation.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.excrptions.IncompatibleComponentException;

import java.time.Instant;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), Instant.now()));
    }

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ErrorResponse> handleDomainValidationException(DomainValidationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), Instant.now()));
    }

    @ExceptionHandler(IncompatibleComponentException.class)
    public ResponseEntity<ErrorResponse> handleIncompatibleComponentException(IncompatibleComponentException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(exception.getMessage(), HttpStatus.CONFLICT.value(), Instant.now()));
    }
}
