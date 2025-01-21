package org.mmga.spring.boot.starter.aspect;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.mmga.spring.boot.starter.componet.JwtUtils;
import org.mmga.spring.boot.starter.entities.Result;
import org.mmga.spring.boot.starter.exception.ServerException;
import org.mmga.spring.boot.starter.interfaces.IdHolder;
import org.mmga.spring.boot.starter.properties.AuthorizationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthMappingHandler {
    private final JwtUtils jwtUtils;
    private final AuthorizationProperties properties;


    public Long getUserIdFromResponse(Object response) {
        if (response instanceof Result<?> result) {
            return getUserIdFromResponse(result.getData());
        }
        if (response instanceof Long userId) {
            return userId;
        }
        if (response instanceof IdHolder idHolder) {
            return idHolder.getId();
        }
        return null;
    }

    public String getTokenFromResponse(Object response) {
        Long userId = getUserIdFromResponse(response);
        if (userId == null) return null;
        return jwtUtils.createToken(userId);
    }

    @AfterReturning(value = "@annotation(org.mmga.spring.boot.starter.annotation.AuthMapping)", returning = "returnValue")
    public void afterAuthMapping(Object returnValue) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            HttpServletResponse response = servletRequestAttributes.getResponse();
            if (response == null) return;
            String token = getTokenFromResponse(returnValue);
            if (token == null) {
                if (returnValue instanceof Result<?> result) {
                    if (!result.isOK()) return;
                }
                throw new ServerException("登录失败，服务端未找到可用的用户id");
            }
            String setAuthorizationHeader = properties.getSetAuthorizationHeader();
            response.setHeader(setAuthorizationHeader, token);
        }
    }
}
