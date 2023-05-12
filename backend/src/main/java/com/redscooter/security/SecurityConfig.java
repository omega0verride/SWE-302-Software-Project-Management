package com.redscooter.security;

import com.redscooter.API.appUser.AppUserService;
import com.redscooter.config.configProperties.CorsConfigProperties;
import com.redscooter.security.filters.CustomAuthenticationFilter;
import com.redscooter.security.filters.CustomAuthorizationFilter;
import com.redscooter.security.thirdPartyLogin.MultiAuthIdentityProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {
    private final JwtUtils jwtUtils;

    @Autowired
    public SecurityConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Autowired
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager, CustomAuthorizationFilter customAuthorizationFilter, AppUserService appUserService, MultiAuthIdentityProvider multiAuthIdentityProvider) throws Exception {
        http.csrf().disable().cors().and().httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // high priority filter that must be accessed only on specific conditions
        http.authorizeHttpRequests((authorizer) -> authorizer.requestMatchers("/api/**").permitAll());

        // open/public endpoints with permitAll !!pay extra attention as order matters in this config first matcher gets returned!!
        // CustomAuthorizationFilter permits authentication and token refresh endpoints by default
//        customAuthorizationFilter.addPublicUnprotectedEndpointsAntMatcher("/api/**");
        customAuthorizationFilter.addPublicUnprotectedEndpointsAntMatcher("/resources/public/**");
        customAuthorizationFilter.addPublicUnprotectedEndpointsAntMatcher("/api-docs/**");
        customAuthorizationFilter.addPublicUnprotectedEndpointsAntMatcher("/api"); // redirect to /api-docs
        for (String antPatter : customAuthorizationFilter.getPublicUnprotectedEndpointsAntMatchers()) {
            http.authorizeHttpRequests((authorizer) -> authorizer.requestMatchers(antPatter).permitAll());
        }

        // deny all other requests (this helps limit access to resources paths)
        http.authorizeHttpRequests((authorizer) -> authorizer.requestMatchers("/**").denyAll());

        http.addFilter(new CustomAuthenticationFilter(authenticationManager, jwtUtils, appUserService, multiAuthIdentityProvider)); // add authentication filter
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // add authorization filter

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter(CorsConfigProperties corsConfigProperties) {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        if (corsConfigProperties.getAllowedOrigins() != null)
            config.setAllowedOrigins(corsConfigProperties.getAllowedOrigins());
        if (corsConfigProperties.getAllowedMethods() != null)
            config.setAllowedMethods(corsConfigProperties.getAllowedMethods());
        if (corsConfigProperties.getAllowedHeaders() != null)
            config.setAllowedHeaders(corsConfigProperties.getAllowedHeaders());
        if (corsConfigProperties.getExposedHeaders() != null)
            config.setExposedHeaders(corsConfigProperties.getExposedHeaders());
        if (corsConfigProperties.getMaxAge() != null)
            config.setMaxAge(corsConfigProperties.getMaxAge());
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}


