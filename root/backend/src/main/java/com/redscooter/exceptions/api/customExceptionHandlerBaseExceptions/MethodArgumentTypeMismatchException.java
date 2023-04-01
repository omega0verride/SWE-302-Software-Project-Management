package com.redscooter.exceptions.api.customExceptionHandlerBaseExceptions;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

@Getter
@Setter
public class MethodArgumentTypeMismatchException extends TypeMismatchException {
    public MethodArgumentTypeMismatchException(Object value, String argument, Type requiredType, Exception rootException) {
        super(value, argument, requiredType, rootException);
    }

    public MethodArgumentTypeMismatchException(Object value, String argument, Type requiredType) {
        this(value, argument, requiredType, null);
    }

    public MethodArgumentTypeMismatchException(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        this(ex.getValue(), ex.getName(), ex.getRequiredType(), ex);
    }
}
