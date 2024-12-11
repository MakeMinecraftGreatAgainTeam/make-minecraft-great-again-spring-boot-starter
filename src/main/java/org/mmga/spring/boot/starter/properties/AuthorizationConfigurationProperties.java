package org.mmga.spring.boot.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "mmga.auth")
@Data
public class AuthorizationConfigurationProperties {
    private String hmacKey = "RANDOM";
    private Env env = Env.DEV;
    private int tokenLifeTimeHour = 24 * 5;
}
