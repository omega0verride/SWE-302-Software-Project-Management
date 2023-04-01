package com.redscooter.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnknownException extends BaseException {
    public UnknownException(String message, Exception rootException) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
        setRootException(rootException);
    }

    public UnknownException(Exception rootException) {
        // TODO: a cool idea would be to generate a stack trace/log and save it in a resources folder accessible only from admins we can send the user a link that points to this file so they can share it with us
        this("Something went wrong, and we did not handle it correctly. Please make sure the request is correct or reach out to the dev team.", rootException);
    }
}
