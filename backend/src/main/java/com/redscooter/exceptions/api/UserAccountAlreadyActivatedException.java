package com.redscooter.exceptions.api;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UserAccountAlreadyActivatedException extends BaseException {
    public UserAccountAlreadyActivatedException() {
        super(HttpStatus.CONFLICT, "This user account is already activated!");
    }
}
