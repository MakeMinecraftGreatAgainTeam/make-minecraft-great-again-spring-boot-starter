package org.mmga.spring.boot.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文档相关配置
 */
@ConfigurationProperties(prefix = "mmga.docs")
@Data
public class DocsProperties {
    /**
     * 文档标题
     */
    private String title = "API文档";
    /**
     * 文档详情
     */
    private String description = "";
}
