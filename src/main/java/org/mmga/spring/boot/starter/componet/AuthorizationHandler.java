package org.mmga.spring.boot.starter.componet;

import jakarta.servlet.http.HttpServletRequest;
import org.mmga.spring.boot.starter.exception.AuthorizationException;
import org.springframework.web.context.request.WebRequest;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * 验证处理类
 * @param <T> 用户信息类型
 */
public interface AuthorizationHandler<T> {
    /**
     * 进行验证
     * @param token token
     * @param ann 注解
     * @return 若登录失败则返回空Optional或抛出AuthorizationException异常，若登录成功则返回用户信息
     */
    Optional<T> auth(String token, Annotation ann);


    /**
     * 对于WebRequest的token处理方式
     * @param request 原始请求
     * @param ann 注解
     * @return 若登录失败则返回空Optional或抛出AuthorizationException异常，若登录成功则返回用户信息
     * @throws AuthorizationException 当验证失败时可抛出
     */
    default Optional<T> auth(WebRequest request, Annotation ann) throws AuthorizationException {
        return auth(request.getHeader("Authorization"), ann);
    }

    /**
     * 对于HttpServletRequest的token处理方式
     * @param request 原始请求
     * @param ann 注解
     * @return 若登录失败则返回空Optional或抛出AuthorizationException异常，若登录成功则返回用户信息
     * @throws AuthorizationException 当验证失败时可抛出
     */
    default Optional<T> auth(HttpServletRequest request, Annotation ann) throws AuthorizationException {
        return auth(request.getHeader("Authorization"), ann);
    }
}
