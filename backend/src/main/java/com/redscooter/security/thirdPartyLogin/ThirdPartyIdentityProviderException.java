package com.redscooter.security.thirdPartyLogin;

import com.redscooter.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ThirdPartyIdentityProviderException extends BaseException {
    public ThirdPartyIdentityProviderException(String message, String customExceptionId) {
        this(message, customExceptionId, false, null);
    }

    public ThirdPartyIdentityProviderException(String message, String customExceptionId, Exception rootException) {
        this(message, customExceptionId, false, rootException);
    }

    public ThirdPartyIdentityProviderException(String message, boolean buildAsInternalServer, Exception rootException) {
        this(message, null, buildAsInternalServer, rootException);
    }

    public ThirdPartyIdentityProviderException(String message) {
        this(message, null);
    }

    public ThirdPartyIdentityProviderException(String message, String customExceptionId, boolean buildAsInternalServer, Exception rootException) {
        super(HttpStatus.UNAUTHORIZED, message, customExceptionId);
        if (buildAsInternalServer)
            buildAsInternalServerException();
        setRootException(rootException);
    }
}
