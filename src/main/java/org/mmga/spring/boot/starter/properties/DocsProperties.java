package org.mmga.spring.boot.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mmga.docs")
@Data
public class DocsProperties {
    private String title = "API文档";
    private String description = "";
}
