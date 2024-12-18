package org.mmga.spring.boot.starter.configuration;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.annotation.Address;
import org.mmga.spring.boot.starter.annotation.AuthMapping;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomDocsAnnotationHandler implements OperationCustomizer {
    private final Class<? extends Annotation> authAnnotation;

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        boolean authRequired = handlerMethod.hasMethodAnnotation(authAnnotation);
        boolean addressRequired = false;
        List<Parameter> parameters = operation.getParameters();
        if (parameters == null) {
            return operation;
        }
        List<String> parameterNamesRemove = new ArrayList<>();
        for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
            String parameterName = methodParameter.getParameter().getName();
            if (methodParameter.hasParameterAnnotation(authAnnotation)) {
                authRequired = true;
                parameterNamesRemove.add(parameterName);
            }
            if (methodParameter.hasParameterAnnotation(Address.class)) {
                parameterNamesRemove.add(parameterName);
                addressRequired = true;
            }
        }
        List<Parameter> newParams = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (parameterNamesRemove.contains(parameter.getName())) continue;
            newParams.add(parameter);
        }
        operation.setParameters(newParams);
        if (authRequired) {
            operation.addExtension("x-auth-required", true);
            operation.description("此接口需要登陆！\n" + Objects.requireNonNullElse(operation.getDescription(), ""));
        }
        if (addressRequired) {
            operation.description("此接口将会收集用户IP数据！\n" + Objects.requireNonNullElse(operation.getDescription(), ""));
        }
        if (handlerMethod.hasMethodAnnotation(AuthMapping.class)) {
            operation.description("此接口为验证接口（请求头中会带有Add-Authorization字段作为登录/注册返回的token）\n" + Objects.requireNonNullElse(operation.getDescription(), ""));
        }
        return operation;
    }
}
