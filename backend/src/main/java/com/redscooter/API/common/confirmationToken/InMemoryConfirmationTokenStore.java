package com.redscooter.API.common.confirmationToken;

import com.redscooter.exceptions.api.ConfirmationTokenNotFoundException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class InMemoryConfirmationTokenStore<T> {
    private final List<ConfirmationToken<T>> confirmationTokens = new ArrayList<>();

    @Setter
    @Getter
    private int maxCapacity = 100;

    public InMemoryConfirmationTokenStore(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public ConfirmationToken<T> generateNewToken(T object) {
        return generateNewToken(object, ConfirmationToken.getDefaultExpiresIn());
    }

    public ConfirmationToken<T> generateNewToken(T object, Long expiresInMillis) {
        ConfirmationToken<T> confirmationToken = new ConfirmationToken<>(object, expiresInMillis);
        if (confirmationTokens.size() >= maxCapacity) {
            confirmationTokens.remove(0);
        }
        confirmationTokens.add(confirmationToken);
        return confirmationToken;
    }

    public T getObjectAndRemoveToken(String token) {
        for (int i = 0; i < confirmationTokens.size(); i++) {
            if (token.equals(confirmationTokens.get(i).getToken())) {
                T objToReturn = confirmationTokens.get(i).getObject();
                confirmationTokens.remove(i);
                return objToReturn;
            }
        }
        throw new ConfirmationTokenNotFoundException();
    }
}
