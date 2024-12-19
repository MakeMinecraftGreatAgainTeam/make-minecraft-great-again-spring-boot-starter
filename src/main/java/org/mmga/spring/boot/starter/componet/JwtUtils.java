package org.mmga.spring.boot.starter.componet;

import java.util.Optional;

public interface JwtUtils {
    String createToken(long uid);

    Optional<Long> verifyToken(String token);
}
