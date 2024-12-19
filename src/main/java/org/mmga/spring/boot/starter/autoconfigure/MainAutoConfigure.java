package org.mmga.spring.boot.starter.autoconfigure;


import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mmga.spring.boot.starter.componet.AuthorizationHandler;
import org.mmga.spring.boot.starter.componet.JwtUtils;
import org.mmga.spring.boot.starter.componet.impl.AuthorizationHandlerImpl;
import org.mmga.spring.boot.starter.componet.impl.JwtUtilsImpl;
import org.mmga.spring.boot.starter.properties.AuthorizationProperties;
import org.mmga.spring.boot.starter.properties.ECKeySave;
import org.mmga.spring.boot.starter.utils.RandomUtils;
import org.mmga.spring.boot.starter.utils.VoUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
@ComponentScan
@EnableConfigurationProperties({AuthorizationProperties.class})
public class MainAutoConfigure {
    public static final String RANDOM_KEY = "RANDOM";

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


    private String generatorHmacKey(RandomUtils randomUtils) {
        return randomUtils.generatorRandomString(16);
    }

    public String getKeyWhenEmpty(AuthorizationProperties authorizationProperties, RandomUtils randomUtils) {
        String hmacKey = authorizationProperties.getHmacKey();
        if (RANDOM_KEY.equals(hmacKey)) {
            return this.generatorHmacKey(randomUtils);
        }
        return hmacKey;
    }

    @SneakyThrows
    private String getJwtKeyContent(AuthorizationProperties authorizationProperties, RandomUtils randomUtils) {
        ECKeySave keySave = authorizationProperties.getKeySave();
        String keySaveFilename = authorizationProperties.getKeySaveFilename();
        if (keySave == ECKeySave.DISK) {
            if (keySaveFilename == null)
                throw new IllegalArgumentException("keySaveFilename cannot be null when keySave is DISK");
            File file = new File(keySaveFilename);
            if (file.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    return new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
            String key = this.getKeyWhenEmpty(authorizationProperties, randomUtils);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(key.getBytes(StandardCharsets.UTF_8));
            }
            return key;
        }
        return this.getKeyWhenEmpty(authorizationProperties, randomUtils);
    }

    @ConditionalOnMissingBean
    @Bean
    public JwtUtils jwtUtils(AuthorizationProperties authorizationProperties, RandomUtils randomUtils) {
        String jwtKeyContent = getJwtKeyContent(authorizationProperties, randomUtils);
        return new JwtUtilsImpl(Algorithm.HMAC512(jwtKeyContent), authorizationProperties);
    }

    @ConditionalOnMissingBean
    @Bean
    public AuthorizationHandler<?> handler(JwtUtils jwtUtils) {
        return new AuthorizationHandlerImpl(jwtUtils);
    }
}
