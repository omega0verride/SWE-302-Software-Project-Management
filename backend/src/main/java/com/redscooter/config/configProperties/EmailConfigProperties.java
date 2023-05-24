package com.redscooter.config.configProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "email")
@Configuration
@Validated
@Data
public class EmailConfigProperties {
    String host = "smtp.gmail.com";
    int port = 587;

    @NotNull
    @NotEmpty
    String username;
    @NotNull
    @NotEmpty
    String password;
    boolean debug = true;
    boolean auth = true;
    boolean starttls = true;
    String protocol = "smtp";
}
