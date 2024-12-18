package org.mmga.spring.boot.starter.configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.utils.AddressArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AddressConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(new AddressArgumentResolver());
    }
}
