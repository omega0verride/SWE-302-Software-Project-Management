package com.redscooter.exceptions.api.forbidden;

import com.redscooter.exceptions.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ForbiddenAccessException extends BaseException {
    public ForbiddenAccessException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
