package com.redscooter.exceptions.api.forbidden;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }
}
