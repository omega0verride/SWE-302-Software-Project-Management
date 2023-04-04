package com.redscooter.exceptions.api.badRequest;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidValueForTypeException extends BaseException {
    public String value;
    public String field;
    public String type;

    public InvalidValueForTypeException(String value, String field, String type, Exception rootException) {
        super(HttpStatus.BAD_REQUEST, String.format("Cannot parse value: '%s' for field: '%s' of type: '%s'", value, field, type));
        setRootException(rootException);
        this.value = value;
        this.field = field;
        this.type = type;
    }

    public InvalidValueForTypeException(String value, String field, String type) {
        this(value, field, type, null);
    }
}
