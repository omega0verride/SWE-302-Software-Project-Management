package com.redscooter.exceptions.api.customExceptionHandlerBaseExceptions;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;

public class TypeMismatchException extends BaseException {

    public Object value;
    public String property;
    public Type requiredType;
    public Type providedType;

    public TypeMismatchException(Object value, String property, Type requiredType, Exception rootException) {
        super(HttpStatus.BAD_REQUEST, String.format("Failed to convert value: '%s' for property: '%s' of type: '%s' to required type '%s'", value, property, value != null ? value.getClass() : null, requiredType));
        setRootException(rootException);
        setValue(value);
        this.property = property;
        this.requiredType = requiredType;
    }

    public TypeMismatchException(Object value, String property, Type requiredType) {
        this(value, property, requiredType, null);
    }

    public TypeMismatchException(org.springframework.beans.TypeMismatchException ex) {
        this(ex.getValue(), ex.getPropertyName(), ex.getRequiredType(), ex);
    }

    private void setValue(Object value) {
        this.value = value;
        if (value != null)
            this.providedType = value.getClass();
    }
}
