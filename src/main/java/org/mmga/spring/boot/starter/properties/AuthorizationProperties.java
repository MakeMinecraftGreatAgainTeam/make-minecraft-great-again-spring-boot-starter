package org.mmga.spring.boot.starter.properties;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 用户验证相关配置
 */
@ConfigurationProperties(prefix = "mmga.auth")
@Data
public class AuthorizationProperties {
    /**
     * jwt使用的hmac密钥（RANDOM为随机密钥）
     */
    private String hmacKey = "RANDOM";
    /**
     * 设置token所使用的请求头
     */
    private String setAuthorizationHeader = "Add-Authorization";
    /**
     * token有效期
     */
    private int tokenLifeTimeHour = 24 * 5;
    /**
     * 密钥的存储方式
     */
    private ECKeySave keySave = ECKeySave.MEMORY;

    /**
     * 密钥的存储文件名（当keySave为DISK时不可为空）
     * @see #keySave
     * @see ECKeySave
     */
    @Nullable
    private String keySaveFilename = null;
}
