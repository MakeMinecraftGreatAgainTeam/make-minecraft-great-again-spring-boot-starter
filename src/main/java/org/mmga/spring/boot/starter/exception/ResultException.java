package org.mmga.spring.boot.starter.exception;

import org.mmga.spring.boot.starter.entities.Result;
import org.springframework.http.HttpStatus;

/**
 * 异常类
 */
public class ResultException extends BaseResultException {
    /**
     * @param httpStatus http状态码
     * @param message 消息
     */
    public ResultException(HttpStatus httpStatus, String message) {
        super(Result.failed(httpStatus, message));
    }
}
