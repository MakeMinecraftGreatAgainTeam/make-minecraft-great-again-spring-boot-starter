package org.mmga.spring.boot.starter.configuration;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.annotation.Address;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomDocsAnnotationHandler implements OperationCustomizer {
    private final Class<? extends Annotation> authAnnotation;

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        if (handlerMethod.hasMethodAnnotation(authAnnotation)) {
            operation.addExtension("x-auth-required", true);
        }
        List<Parameter> parameters = operation.getParameters();
        if (parameters == null) {
            return operation;
        }
        List<String> parameterNamesRemove = new ArrayList<>();
        for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
            String parameterName = methodParameter.getParameter().getName();
            if (methodParameter.hasParameterAnnotation(authAnnotation)) {
                operation.addExtension("x-auth-required", true);
                parameterNamesRemove.add(parameterName);
            }
            if (methodParameter.hasParameterAnnotation(Address.class)) {
                parameterNamesRemove.add(parameterName);
            }
        }
        List<Parameter> newParams = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (parameterNamesRemove.contains(parameter.getName())) continue;
            newParams.add(parameter);
        }
        operation.setParameters(newParams);
        return operation;
    }
}
