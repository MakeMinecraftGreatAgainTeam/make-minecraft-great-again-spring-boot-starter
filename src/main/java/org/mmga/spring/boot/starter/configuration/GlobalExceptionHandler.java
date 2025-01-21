package org.mmga.spring.boot.starter.configuration;

import org.mmga.spring.boot.starter.entities.Result;
import org.mmga.spring.boot.starter.exception.BaseResultException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseResultException.class)
    public ResponseEntity<Result<?>> handleAuthorizationException(BaseResultException e) {
        Result<?> result = e.getResult();
        return ResponseEntity.ok(result);
    }
}