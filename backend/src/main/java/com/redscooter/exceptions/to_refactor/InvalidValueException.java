package com.redscooter.exceptions.to_refactor;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidValueException extends BaseException {
    public InvalidValueException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
