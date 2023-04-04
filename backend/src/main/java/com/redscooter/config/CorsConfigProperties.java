package com.redscooter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "cors")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CorsConfigProperties {

    private List<String> allowedOrigins;

    private List<String> allowedMethods;

    private List<String> allowedHeaders;

    private List<String> exposedHeaders;

    private Long maxAge = null;

}
