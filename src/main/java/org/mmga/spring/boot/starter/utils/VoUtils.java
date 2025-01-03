package org.mmga.spring.boot.starter.utils;

import lombok.SneakyThrows;
import org.mmga.spring.boot.starter.annotation.VoFieldMapper;
import org.mmga.spring.boot.starter.annotation.VoIgnore;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class VoUtils {
    public <T> T vo2Dto(Object vo, Class<T> dtoClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> noArgsConstructor = dtoClass.getDeclaredConstructor();
        noArgsConstructor.setAccessible(true);
        T instance = noArgsConstructor.newInstance();
        return vo2Dto(vo, instance);
    }

    @SneakyThrows
    public <T> T vo2DtoSafe(Object vo, Class<T> dtoClass) {
        return this.vo2Dto(vo, dtoClass);
    }

    public <T> T vo2Dto(Object vo, T emptyDto) throws IllegalAccessException, InvocationTargetException {
        Class<?> aClass = emptyDto.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        Map<String, Method> setterMap = new HashMap<>();
        Map<String, Field> fieldMap = new HashMap<>();
        for (Method declaredMethod : declaredMethods) {
            String name = declaredMethod.getName();
            if (name.startsWith("set")) {
                if (declaredMethod.getAnnotation(VoIgnore.class) != null) continue;
                String attrName = name.replaceFirst("set", "");
                declaredMethod.setAccessible(true);
                VoFieldMapper mapper = declaredMethod.getAnnotation(VoFieldMapper.class);
                if (mapper != null) {
                    setterMap.put(mapper.value(), declaredMethod);
                    continue;
                }
                setterMap.put(attrName, declaredMethod);
            }
        }
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.getAnnotation(VoIgnore.class) != null) continue;
            declaredField.setAccessible(true);
            VoFieldMapper mapper = declaredField.getAnnotation(VoFieldMapper.class);
            if (mapper != null) {
                fieldMap.put(mapper.value(), declaredField);
                continue;
            }
            fieldMap.put(declaredField.getName(), declaredField);
        }
        Class<?> voClass = vo.getClass();
        Field[] fields = voClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(VoIgnore.class) != null) continue;
            String fieldName = field.getName();
            Method method = setterMap.get(fieldName);
            if (method != null) {
                method.invoke(emptyDto, field.get(vo));
                continue;
            }
            Field field1 = fieldMap.get(fieldName);
            if (field1 != null) {
                field1.set(emptyDto, field.get(vo));
            }
        }
        return emptyDto;
    }

    @SneakyThrows
    public <T> T vo2DtoSafe(Object vo, T emptyDto) {
        return this.vo2Dto(vo, emptyDto);
    }
}
