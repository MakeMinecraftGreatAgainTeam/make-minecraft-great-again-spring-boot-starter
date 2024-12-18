package org.mmga.spring.boot.starter.autoconfigure;


import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.componet.AuthorizationHandler;
import org.mmga.spring.boot.starter.componet.JwtUtils;
import org.mmga.spring.boot.starter.componet.impl.AuthorizationHandlerImpl;
import org.mmga.spring.boot.starter.componet.impl.JwtUtilsImpl;
import org.mmga.spring.boot.starter.properties.AuthorizationProperties;
import org.mmga.spring.boot.starter.utils.RandomUtils;
import org.mmga.spring.boot.starter.utils.VoUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ComponentScan
@EnableConfigurationProperties({AuthorizationProperties.class})
public class MainAutoConfigure {
    @ConditionalOnMissingBean
    @Bean
    public VoUtils voUtils() {
        return new VoUtils();
    }

    @ConditionalOnMissingBean
    @Bean
    public RandomUtils getRandomUtils() {
        return new RandomUtils();
    }

    @ConditionalOnMissingBean
    @Bean
    public JwtUtils jwtUtils(AuthorizationProperties authorizationProperties, RandomUtils randomUtils) {
        return new JwtUtilsImpl(authorizationProperties, randomUtils);
    }

    @ConditionalOnMissingBean
    @Bean
    public AuthorizationHandler<?> handler(JwtUtils jwtUtils) {
        return new AuthorizationHandlerImpl(jwtUtils);
    }
}
