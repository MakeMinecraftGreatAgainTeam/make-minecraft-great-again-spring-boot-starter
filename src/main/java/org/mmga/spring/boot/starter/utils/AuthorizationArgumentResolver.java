package org.mmga.spring.boot.starter.utils;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.componet.AuthorizationHandler;
import org.mmga.spring.boot.starter.entities.Result;
import org.mmga.spring.boot.starter.exception.AuthorizationException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorizationArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthorizationHandler<?> handler;
    private final Class<? extends Annotation> authAnnotation;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(authAnnotation);
    }

    @Override
    @Nullable
    public Object resolveArgument(@NonNull MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws AuthorizationException {
        Annotation parameterAnnotation = parameter.getParameterAnnotation(authAnnotation);
        assert parameterAnnotation != null;
        Optional<?> auth = this.handler.auth(webRequest, parameterAnnotation);
        if (auth.isEmpty()) {
            throw new AuthorizationException(Result.failed(HttpStatus.UNAUTHORIZED, "缺少token"));
        }
        return auth.get();
    }
}