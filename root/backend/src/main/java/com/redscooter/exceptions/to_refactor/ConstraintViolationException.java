package com.redscooter.exceptions.to_refactor;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ConstraintViolationException extends BaseException {

    public ConstraintViolationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
