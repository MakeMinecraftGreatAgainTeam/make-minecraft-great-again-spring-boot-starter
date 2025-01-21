package org.mmga.spring.boot.starter.exception;

import org.mmga.spring.boot.starter.entities.Result;
import org.springframework.http.HttpStatus;

/**
 * 登录失败错误
 */
public class AuthorizationException extends BaseResultException {

    /**
     * @deprecated 此方法不安全
     * @see #AuthorizationException(String)
     * @param result 使用result
     */
    @Deprecated
    public AuthorizationException(Result<Void> result) {
        super(result);
        super.setResult(result);
    }

    /**
     将会发送401状态码
     * @param message 消息
     */
    public AuthorizationException(String message) {
        super(Result.failed(HttpStatus.UNAUTHORIZED, message));
    }
}