package org.mmga.spring.boot.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mmga")
@Data
public class MainProperties {
    /**
     * 环境类型
     */
    private Env env = Env.DEV;
}
