package org.mmga.spring.boot.starter.exception;

import lombok.Getter;
import org.mmga.spring.boot.starter.entities.Result;

@Getter
public class AuthorizationException extends RuntimeException {
    private final Result<Void> result;

    public AuthorizationException(Result<Void> result) {
        super(result.getMsg());
        this.result = result;
    }
}