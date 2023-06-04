package com.redscooter.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Bean
    public OperationCustomizer customGlobalHeaders() {

        return (Operation operation, HandlerMethod handlerMethod) -> {

            Parameter missingParam1 = new Parameter()
                    .in(ParameterIn.HEADER.toString())
                    .schema(new StringSchema())
                    .name("Authorization")
                    .description("JWT Bearer Authorization Header")
                    .required(false);


            if (operation.getParameters()!=null &&operation.getParameters().stream().noneMatch(p-> p.getName().equalsIgnoreCase("authorization")))
                operation.addParametersItem(missingParam1);

            return operation;
        };
    }
}
