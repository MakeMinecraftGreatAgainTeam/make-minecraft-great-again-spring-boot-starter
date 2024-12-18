package org.mmga.spring.boot.starter.properties;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "mmga.auth")
@Data
public class AuthorizationProperties {
    private String hmacKey = "RANDOM";
    private int tokenLifeTimeHour = 24 * 5;
    private ECKeySave keySave = ECKeySave.MEMORY;
    @Nullable
    private String keySaveFilename = null;
}
