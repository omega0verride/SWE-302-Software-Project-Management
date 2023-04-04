package com.redscooter.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class Config {

    @Bean
    CommandLineRunner mainConfigCommandLineRunner() {
        return args -> {
            TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        };
    }
}

