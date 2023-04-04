package com.redscooter.exceptions.api.unauthorized;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidTokenTypeException extends InvalidTokenException {
    public String expectedTokenType;
    public String providedTokenType;

    public InvalidTokenTypeException(String expectedTokenType, String providedTokenType) {
        super(String.format("Expected token type: '%s' provided: '%s'", expectedTokenType, providedTokenType));
        setExpectedTokenType(expectedTokenType);
        setProvidedTokenType(providedTokenType);
    }
}
