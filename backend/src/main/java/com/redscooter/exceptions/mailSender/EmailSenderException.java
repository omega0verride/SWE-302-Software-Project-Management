package com.redscooter.exceptions.mailSender;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public abstract class EmailSenderException extends BaseException {
    public EmailSenderException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public EmailSenderException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public EmailSenderException() {
        super();
    }
}