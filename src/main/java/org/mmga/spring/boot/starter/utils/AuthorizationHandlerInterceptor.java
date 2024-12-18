package org.mmga.spring.boot.starter.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.componet.AuthorizationHandler;
import org.mmga.spring.boot.starter.entities.Result;
import org.mmga.spring.boot.starter.exception.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorizationHandlerInterceptor implements HandlerInterceptor {
    private final AuthorizationHandler<?> handler;
    private final Class<? extends Annotation> authAnnotation;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws AuthorizationException {
        if (handler instanceof HandlerMethod method) {
            if (!method.hasMethodAnnotation(authAnnotation)) {
                return true;
            }
            Annotation annotation = method.getMethodAnnotation(authAnnotation);
            Optional<?> auth = this.handler.auth(request, annotation);
            if (auth.isEmpty()) {
                throw new AuthorizationException(Result.failed(HttpStatus.UNAUTHORIZED, "缺少token"));
            }
            return true;
        }
        return true;
    }
}