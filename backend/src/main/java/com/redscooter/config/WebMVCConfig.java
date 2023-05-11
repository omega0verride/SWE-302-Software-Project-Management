package com.redscooter.config;

import com.redscooter.util.Utilities;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Register resource handler for images
        registry.addResourceHandler("/resources/**").addResourceLocations("file:" + Utilities.getResourcesPath_() + "/")
                .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS)
                        .noTransform()
                        .mustRevalidate());
    }


    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory>
    containerCustomizer() {
        return new EmbeddedTomcatCustomizer();
    }

    private static class EmbeddedTomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

        @Override
        public void customize(TomcatServletWebServerFactory factory) {
            factory.addConnectorCustomizers(connector -> {
                connector.setProperty("relaxedPathChars", "<>[\\]^`{|}");
                connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}");
            });
        }
    }
}


