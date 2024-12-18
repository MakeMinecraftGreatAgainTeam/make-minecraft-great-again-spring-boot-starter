package org.mmga.spring.boot.starter.configuration;

import org.mmga.spring.boot.starter.annotation.Auth;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

@Configuration
@ComponentScan
public class DefaultConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public Class<? extends Annotation> authAnnotation() {
        return Auth.class;
    }
}
