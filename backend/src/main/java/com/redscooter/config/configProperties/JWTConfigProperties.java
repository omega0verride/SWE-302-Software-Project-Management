package com.redscooter.config.configProperties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@ConfigurationProperties(prefix = "jwt")
@Configuration
@Validated
@Data
public class JWTConfigProperties {
    public static final String DEFAULT_ACCESS_TOKEN_ENDPOINT_VALUE = "/api/token";
    public static final String DEFAULT_REFRESH_TOKEN_ENDPOINT_VALUE = "/api/token/refresh";

    @NotNull
    @NotEmpty
    private String secret;

    @NotNull
    @NotEmpty
    private String issuer;

    private AccessTokenProperties accessToken;

    private RefreshTokenProperties refreshToken;

    @Data
    public static class AccessTokenProperties {
        String endpoint = DEFAULT_ACCESS_TOKEN_ENDPOINT_VALUE;
        @Min(1000)
        Long expiresIn = 7776000000L;
    }

    @Data
    public static class RefreshTokenProperties {
        String endpoint = DEFAULT_REFRESH_TOKEN_ENDPOINT_VALUE;
        @Min(1000)
        Long expiresIn = 43200000L;
    }
}