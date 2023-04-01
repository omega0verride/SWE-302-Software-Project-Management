package com.redscooter.exceptions.generic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDecodeException extends RuntimeException {
    String message;
    Exception rootException;

    public TokenDecodeException(Exception e) {
        this(null, e);
    }

    public TokenDecodeException(String message, Exception rootException) {
        super("Could not decode token! " + message + "\n" + rootException);
        this.message = "Could not decode token! " + message;
        this.rootException = rootException;
    }


}
