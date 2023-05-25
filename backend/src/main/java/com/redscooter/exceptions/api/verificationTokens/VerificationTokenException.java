package com.redscooter.exceptions.api.verificationTokens;

import com.redscooter.exceptions.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class VerificationTokenException extends BaseException {
    public Long expiresAt = null;

    public VerificationTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid Or Expired Token!");
    }

    public VerificationTokenException(Long expiredAt) {
        this.expiresAt = expiredAt;
    }

    public VerificationTokenException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}

