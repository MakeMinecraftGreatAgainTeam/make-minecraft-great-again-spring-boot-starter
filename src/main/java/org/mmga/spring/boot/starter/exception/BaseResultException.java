package org.mmga.spring.boot.starter.exception;

import lombok.*;
import org.mmga.spring.boot.starter.entities.Result;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseResultException extends RuntimeException {
    private Result<?> result;
}
