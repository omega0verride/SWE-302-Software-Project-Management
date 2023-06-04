package com.redscooter.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("redscooter.al Rest API")
                        .description("API documentation for REDSCOOTER. Please note that most filtering/sorting capabilities are not fully documented. Use the metadata endpoints to learn more.")
                        .version("1.0")
                        .contact(new Contact().name("Indrit Breti").url("https://github.com/omega0verride").email("indritbreti@gmail.com"))
                        .license(null))
                .components(new Components()
                );
    }
}
