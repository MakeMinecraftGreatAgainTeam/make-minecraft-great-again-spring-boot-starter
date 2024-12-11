package org.mmga.spring.boot.starter.configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.properties.AuthorizationConfigurationProperties;
import org.mmga.spring.boot.starter.properties.Env;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfiguration implements WebMvcConfigurer {
    private final AuthorizationConfigurationProperties properties;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        if (properties.getEnv().equals(Env.DEV)) {
            registry.addMapping("/**") //允许所有路径进行CORS
                    .allowedHeaders("*")
                    .exposedHeaders("Add-Authorization")
                    .allowedMethods("*"); //允许所有请求方式
        }
    }
}
