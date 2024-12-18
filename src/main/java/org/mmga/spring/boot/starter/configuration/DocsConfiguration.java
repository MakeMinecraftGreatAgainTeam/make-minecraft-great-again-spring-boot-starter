package org.mmga.spring.boot.starter.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.properties.DocsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({DocsProperties.class})
public class DocsConfiguration {

    private final DocsProperties docsProperties;

    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info().title(docsProperties.getTitle()).description(docsProperties.getDescription()));
    }
}
