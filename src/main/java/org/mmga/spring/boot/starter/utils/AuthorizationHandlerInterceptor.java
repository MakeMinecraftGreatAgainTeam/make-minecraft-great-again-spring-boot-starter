package org.mmga.spring.boot.starter.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.componet.AuthorizationHandler;
import org.mmga.spring.boot.starter.exception.AuthorizationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.Annotation;

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
            return this.handler.auth(request, annotation).isPresent();
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull Object handler, ModelAndView modelAndView) {
        System.out.println(modelAndView);
    }
}