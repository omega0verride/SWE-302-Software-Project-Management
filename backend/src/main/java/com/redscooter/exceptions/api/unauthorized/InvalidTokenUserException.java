package com.redscooter.exceptions.api.unauthorized;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidTokenUserException extends InvalidTokenException {
    public String username;

    public InvalidTokenUserException(String username) {
        super(String.format("The user: '%s' for which this token was issued does not exist.", username));
        setUsername(username);
    }
}
