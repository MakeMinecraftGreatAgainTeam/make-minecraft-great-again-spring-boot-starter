package org.mmga.spring.boot.starter.configuration;

import org.mmga.spring.boot.starter.autoconfigure.MainAutoConfigure;
import org.mmga.spring.boot.starter.componet.AuthorizationHandler;
import org.mmga.spring.boot.starter.utils.AuthorizationArgumentResolver;
import org.mmga.spring.boot.starter.utils.AuthorizationHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.Annotation;
import java.util.List;

@Configuration
@AutoConfigureAfter(MainAutoConfigure.class)
public class AuthorizationConfiguration implements WebMvcConfigurer {
    private final AuthorizationArgumentResolver authorizationArgumentResolver;
    private final AuthorizationHandlerInterceptor authorizationHandlerInterceptor;

    @Autowired
    public AuthorizationConfiguration(AuthorizationHandler<?> authorizationHandler, Class<? extends Annotation> authAnnotation) {
        this.authorizationArgumentResolver = new AuthorizationArgumentResolver(authorizationHandler, authAnnotation);
        this.authorizationHandlerInterceptor = new AuthorizationHandlerInterceptor(authorizationHandler, authAnnotation);
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(authorizationArgumentResolver);
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(authorizationHandlerInterceptor);
    }
}