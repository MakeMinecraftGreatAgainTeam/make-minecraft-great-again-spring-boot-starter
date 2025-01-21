package org.mmga.spring.boot.starter.exception;

import org.mmga.spring.boot.starter.entities.Result;
import org.springframework.http.HttpStatus;

/**
 * 服务端错误（500 Internal Server Error）
 */
public class ServerException extends BaseResultException {
    /**
     * 将会返回500
     * @param message 消息
     */
    public ServerException(String message) {
        super(Result.failed(HttpStatus.INTERNAL_SERVER_ERROR, message));
    }
}
