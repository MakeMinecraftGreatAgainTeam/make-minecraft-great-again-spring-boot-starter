package org.mmga.spring.boot.starter.componet;

import jakarta.servlet.http.HttpServletRequest;
import org.mmga.spring.boot.starter.exception.AuthorizationException;
import org.springframework.web.context.request.WebRequest;

import java.lang.annotation.Annotation;
import java.util.Optional;

public interface AuthorizationHandler<T> {
    Optional<T> auth(String token, Annotation ann);

    default Optional<T> auth(WebRequest request, Annotation ann) throws AuthorizationException {
        return auth(request.getHeader("Authorization"), ann);
    }

    default Optional<T> auth(HttpServletRequest request, Annotation ann) throws AuthorizationException {
        return auth(request.getHeader("Authorization"), ann);
    }
}
