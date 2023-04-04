package com.redscooter.security.filters;

import com.redscooter.API.appUser.AppUserService;
import com.redscooter.exceptions.BaseException;
import com.redscooter.exceptions.GlobalResponseEntityExceptionHandler;
import com.redscooter.exceptions.UnknownException;
import com.redscooter.exceptions.api.unauthorized.*;
import com.redscooter.security.JwtUtils;
import com.redscooter.security.TokenDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final List<String> PUBLIC_UNPROTECTED_ENDPOINTS_ANT_MATCHERS = new ArrayList<>();
    private final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    JwtUtils jwtUtils;
    AppUserService appUserService;
    GlobalResponseEntityExceptionHandler globalResponseEntityExceptionHandler;

    @Autowired
    public CustomAuthorizationFilter(JwtUtils jwtUtils, AppUserService appUserService, GlobalResponseEntityExceptionHandler globalResponseEntityExceptionHandler) {
        this.jwtUtils = jwtUtils;
        this.appUserService = appUserService;
        this.globalResponseEntityExceptionHandler = globalResponseEntityExceptionHandler;
        addPublicUnprotectedEndpointsAntMatcher(jwtUtils.getAccessTokenEndpoint());
        addPublicUnprotectedEndpointsAntMatcher(jwtUtils.getRefreshTokenEndpoint());
    }

    public void addPublicUnprotectedEndpointsAntMatcher(String antPattern) {
        if (!PUBLIC_UNPROTECTED_ENDPOINTS_ANT_MATCHERS.contains(antPattern))
            PUBLIC_UNPROTECTED_ENDPOINTS_ANT_MATCHERS.add(antPattern);
    }

    public List<String> getPublicUnprotectedEndpointsAntMatchers() {
        return new ArrayList<>(PUBLIC_UNPROTECTED_ENDPOINTS_ANT_MATCHERS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        for (String antPattern : PUBLIC_UNPROTECTED_ENDPOINTS_ANT_MATCHERS) {
            if (ANT_PATH_MATCHER.match(antPattern, request.getServletPath())) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            TokenDetails tokenDetails = jwtUtils.getTokenDetailsFromAuthorizationHeader(authorizationHeader, appUserService);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tokenDetails.getAppUser().getUsername(), null, tokenDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            if (!(exception instanceof BaseException))
                exception = new UnknownException(exception);
            BaseException baseException = ((BaseException) exception);
            response.setStatus(baseException.getHttpStatusCode());
            response.setContentType(APPLICATION_JSON_VALUE);
            logger.error(baseException);
            try {
                new ObjectMapper().writeValue(response.getOutputStream(), baseException.toErrorResponse());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
