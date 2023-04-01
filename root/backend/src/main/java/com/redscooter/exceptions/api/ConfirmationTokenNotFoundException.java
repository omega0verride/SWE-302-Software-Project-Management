package com.redscooter.exceptions.api;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ConfirmationTokenNotFoundException extends BaseException {
    public ConfirmationTokenNotFoundException() {
        super(HttpStatus.CONFLICT, "The confirmation token is invalid, has already been used or is expired!");
    }
}
