package com.redscooter.exceptions.api.badRequest;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class BadRequestBodyException extends BaseException {
    public BadRequestBodyException(Exception rootException, String message) {
        super(HttpStatus.BAD_REQUEST, message);
        setRootException(rootException);
    }

    public BadRequestBodyException(Exception rootException) {
        this(rootException, "Invalid Request Body!");
    }

    public BadRequestBodyException(String message) {
        this(null, message);
    }

    public BadRequestBodyException() {
        this((Exception) null);
    }
}
