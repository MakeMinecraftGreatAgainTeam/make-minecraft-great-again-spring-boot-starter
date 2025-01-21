package org.mmga.spring.boot.starter.componet;

import java.util.Optional;

/**
 * jwt处理接口
 */
public interface JwtUtils {
    /**
     * 通过用户ID生成jwt
     * @param uid 用户ID
     * @return jwtToken
     */
    String createToken(long uid);

    /**
     * 验证token
     * @param token token
     * @return 若成功则返回用户ID，否则返回空Optional
     */
    Optional<Long> verifyToken(String token);
}
