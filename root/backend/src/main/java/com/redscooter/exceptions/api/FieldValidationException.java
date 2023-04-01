package com.redscooter.exceptions.api;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class FieldValidationException extends BaseException {

    public FieldValidationException() {
        super(HttpStatus.BAD_REQUEST, "");
    }

}
