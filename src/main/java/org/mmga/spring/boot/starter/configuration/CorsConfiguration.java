package org.mmga.spring.boot.starter.configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.properties.AuthorizationProperties;
import org.mmga.spring.boot.starter.properties.Env;
import org.mmga.spring.boot.starter.properties.MainProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({MainProperties.class, AuthorizationProperties.class})
public class CorsConfiguration implements WebMvcConfigurer {
    private final MainProperties mainProperties;
    private final AuthorizationProperties properties;


    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        if (mainProperties.getEnv().equals(Env.DEV)) {
            registry.addMapping("/**") //允许所有路径进行CORS
                    .allowedHeaders("*")
                    .exposedHeaders(properties.getSetAuthorizationHeader())
                    .allowedMethods("*"); //允许所有请求方式
        }
    }
}
