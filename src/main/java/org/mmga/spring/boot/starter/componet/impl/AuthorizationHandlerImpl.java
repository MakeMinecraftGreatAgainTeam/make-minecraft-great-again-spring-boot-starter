package org.mmga.spring.boot.starter.componet.impl;

import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.componet.AuthorizationHandler;
import org.mmga.spring.boot.starter.componet.JwtUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorizationHandlerImpl implements AuthorizationHandler<Integer> {
    private final JwtUtils jwtUtils;

    @Override
    public Optional<Integer> auth(String token, Annotation ann) {
        return jwtUtils.verifyToken(token);
    }
}
