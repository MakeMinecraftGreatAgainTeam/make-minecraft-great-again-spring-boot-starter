package org.mmga.spring.boot.starter.componet.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.mmga.spring.boot.starter.componet.JwtUtils;
import org.mmga.spring.boot.starter.properties.AuthorizationConfigurationProperties;
import org.mmga.spring.boot.starter.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Optional;

@Component
@Slf4j
public class JwtUtilsImpl implements JwtUtils {
    private final Algorithm hmacKey;
    private final RandomUtils randomUtils;
    private final AuthorizationConfigurationProperties properties;

    private String generatorHmacKey() {
        return this.randomUtils.generatorRandomString(16);
    }

    @Autowired
    public JwtUtilsImpl(AuthorizationConfigurationProperties properties, RandomUtils randomUtils) {
        this.properties = properties;
        this.randomUtils = randomUtils;
        String hmacKey = properties.getHmacKey();
        String key;
        if ("RANDOM".equalsIgnoreCase(hmacKey)) {
            key = this.generatorHmacKey();
            log.info("Using Random Hmac Key: {}", key);
        } else {
            key = hmacKey;
        }
        this.hmacKey = Algorithm.HMAC512(key);
    }

    public String createToken(long uid) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, this.properties.getTokenLifeTimeHour());
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("uid", uid);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(this.hmacKey);
    }

    public Optional<Integer> verifyToken(String token) {
        DecodedJWT verify;
        try {
            verify = JWT.require(this.hmacKey).build().verify(token);
        } catch (Exception e) {
            return Optional.empty();
        }
        Claim uid = verify.getClaim("uid");
        return Optional.of(uid.asInt());
    }
}
