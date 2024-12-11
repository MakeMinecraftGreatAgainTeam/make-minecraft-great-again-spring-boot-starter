package org.mmga.spring.boot.starter.configuration;

import org.mmga.spring.boot.starter.annotation.Auth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

@Configuration
public class DefaultConfiguration {
    @Bean
    public Class<? extends Annotation> authAnnotation() {
        return Auth.class;
    }
}
