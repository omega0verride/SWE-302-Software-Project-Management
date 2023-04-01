package com.redscooter.API.common.confirmationToken;

import lombok.Getter;

import java.util.UUID;

public class ConfirmationToken<T> {
    private static final long EXPIRES_IN = 60 * 3 * 1000; // millis

    @Getter
    private final String token;

    @Getter
    private final T object;

    @Getter
    private final Long expiresAt;

    public ConfirmationToken(T object) {
        this(object, EXPIRES_IN);
    }

    public ConfirmationToken(T object, Long expiresInMilliseconds) {
        this.token = UUID.randomUUID().toString();
        this.object = object;
        this.expiresAt = System.currentTimeMillis() + expiresInMilliseconds;
    }

    public boolean isExpired() {
        return (expiresAt - System.currentTimeMillis()) <= 0;
    }

    public boolean isValid() {
        return !isExpired();
    }

    public static Long getDefaultExpiresIn() {
        return EXPIRES_IN;
    }
}
