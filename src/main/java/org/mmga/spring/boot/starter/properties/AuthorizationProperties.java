package org.mmga.spring.boot.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "mmga.auth")
@Data
public class AuthorizationProperties {
    private String hmacKey = "RANDOM";
    private int tokenLifeTimeHour = 24 * 5;
}
