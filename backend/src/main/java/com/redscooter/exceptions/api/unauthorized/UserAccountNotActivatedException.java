package com.redscooter.exceptions.api.unauthorized;

import com.redscooter.exceptions.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserAccountNotActivatedException extends BaseException {
    public UserAccountNotActivatedException() {
        super(HttpStatus.UNAUTHORIZED, "This user account is not enabled! Please make sure you have verified your email address.");
    }
}
