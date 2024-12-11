package org.mmga.spring.boot.starter.configuration;

import org.mmga.spring.boot.starter.entities.Result;
import org.mmga.spring.boot.starter.exception.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Result<Void>> handleAuthorizationException(AuthorizationException e) {
        Result<Void> result = e.getResult();
        return ResponseEntity.ok(result);
    }
}