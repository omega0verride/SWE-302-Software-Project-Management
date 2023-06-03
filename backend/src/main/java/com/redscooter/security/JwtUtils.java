package com.redscooter.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.appUser.AppUserService;
import com.redscooter.config.configProperties.JWTConfigProperties;
import com.redscooter.exceptions.api.unauthorized.InvalidTokenException;
import com.redscooter.exceptions.api.unauthorized.InvalidTokenTypeException;
import com.redscooter.exceptions.api.unauthorized.InvalidTokenUserException;
import com.redscooter.exceptions.api.unauthorized.UserAccountNotActivatedException;
import com.redscooter.exceptions.generic.TokenDecodeException;
import com.redscooter.security.DTO.DetailedTokenDetailsDTO;
import com.redscooter.security.DTO.TokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils implements Serializable {

    @Serial
    private static final long serialVersionUID = -2550185165626007488L;
    private final String jwtSecret;
    private final String jwtIssuer;
    private final Algorithm algorithm;
    private final String accessTokenEndpoint;
    private final String refreshTokenEndpoint;
    private final Long refreshTokenExpiresIn;
    private final String rolesClaimKey = "roles";
    private final String tokenTypeKey = "token_type";
    private final Long accessTokenExpiresIn;

    @Autowired
    public JwtUtils(JWTConfigProperties jwtConfigProperties) {
        this.accessTokenEndpoint = jwtConfigProperties.getAccessToken().getEndpoint();
        this.accessTokenExpiresIn = jwtConfigProperties.getAccessToken().getExpiresIn();
        this.refreshTokenEndpoint = jwtConfigProperties.getRefreshToken().getEndpoint();
        this.refreshTokenExpiresIn = jwtConfigProperties.getRefreshToken().getExpiresIn();
        this.jwtSecret = jwtConfigProperties.getSecret();
        this.jwtIssuer = jwtConfigProperties.getIssuer();

        algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
    }

    public String getTokenType(DecodedJWT token) {
        return token.getClaim(tokenTypeKey).asString();
    }

    public String getTokenType(String token) {
        return getTokenType(decodeToken(token));
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(DecodedJWT token) {
        return token.getSubject();
    }

    public String getUsernameFromToken(String token) {
        return getUsernameFromToken(decodeToken(token));
    }

    //retrieve roles from access token
    public List<String> getRolesFromAccessToken(DecodedJWT token) {
        return token.getClaim(rolesClaimKey).asList(String.class);
    }

    public List<String> getRolesFromAccessToken(String token) {
        return getRolesFromAccessToken(decodeToken(token));
    }

    //retrieve authorities from access token
    public List<SimpleGrantedAuthority> getAuthoritiesFromAccessToken(DecodedJWT token) {
        return token.getClaim(rolesClaimKey).asList(String.class).stream().map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
    }

    public List<SimpleGrantedAuthority> getAuthoritiesFromAccessToken(String token) {
        return getAuthoritiesFromAccessToken(decodeToken(token));
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(DecodedJWT token) {
        return token.getExpiresAt();
    }

    public Date getExpirationDateFromToken(String token) {
        return getExpirationDateFromToken(decodeToken(token));
    }

    private Boolean isTokenExpired(DecodedJWT token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isTokenExpired(String token) {
        return isTokenExpired(decodeToken(token));
    }

    public DecodedJWT decodeToken(String token) {
        try {
            return JWT.require(algorithm).build().verify(token);
        } catch (Exception e) {
            if (e instanceof TokenExpiredException)
                throw e;
            throw new TokenDecodeException(e);
        }
    }

    // ------------- access token -------------
    public String generateAccessToken(User user) {
        HashMap<String, List<String>> claims = new HashMap<>();
        claims.put(rolesClaimKey, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return generateAccessToken(user.getUsername(), claims);
    }

    public String generateAccessToken(String subject, Map<String, ?> claims) {
        return generateAccessToken(subject, claims, this.accessTokenExpiresIn, this.jwtIssuer);
    }

    public String generateAccessToken(String subject, Map<String, ?> claims, Long expiresIn, String issuer) {
        JWTCreator.Builder access_token_builder = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresIn))
                .withIssuer(issuer);
        for (String key : claims.keySet())
            addClaim(access_token_builder, key, claims.get(key));
        access_token_builder.withClaim(tokenTypeKey, TokenType.access_token.toString());
        return access_token_builder.sign(algorithm);
    }
    // ------------- access token -------------


    // ------------- refresh token -------------
    public String generateRefreshToken(User user) {
        return generateRefreshToken(user.getUsername());
    }

    public String generateRefreshToken(String subject) {
        return generateRefreshToken(subject, this.refreshTokenExpiresIn, jwtIssuer);
    }

    public String generateRefreshToken(String subject, Long expiresIn, String issuer) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresIn))
                .withIssuer(issuer)
                .withClaim(tokenTypeKey, TokenType.refresh_token.toString())
                .sign(algorithm);
    }
    // ------------- refresh token -------------

    // ------------- access-refresh-pair -------------
    public TokenPair generateTokenPair(User user) {
        HashMap<String, List<String>> claims = new HashMap<>();
        claims.put(rolesClaimKey, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return generateTokenPair(user.getUsername(), claims);
    }

    public TokenPair generateTokenPair(String subject, Map<String, ?> claims) {
        return generateTokenPair(subject, claims, this.accessTokenExpiresIn, this.refreshTokenExpiresIn, this.jwtIssuer);
    }

    public TokenPair generateTokenPair(String subject, Map<String, ?> claims, Long expiresIn, Long refreshExpiresIn, String issuer) {
        return generateTokenPair(generateAccessToken(subject, claims, expiresIn, issuer), generateRefreshToken(subject, refreshExpiresIn, issuer));
    }

    public TokenPair generateTokenPair(String accessToken, String refreshToken) {
        return new TokenPair(accessToken, refreshToken);
    }

    public DetailedTokenDetailsDTO generateDetailedTokenPair(User user) {
        return generateDetailedTokenPair(generateAccessToken(user), generateRefreshToken(user));
    }

    public DetailedTokenDetailsDTO generateDetailedTokenPair(String accessToken, String refreshToken) {
        TokenPair tokens = generateTokenPair(accessToken, refreshToken);
        return new DetailedTokenDetailsDTO(tokens, getExpirationDateFromToken(tokens.getAccessToken()).getTime(), getRolesFromAccessToken(tokens.getAccessToken()), getUsernameFromToken(tokens.getAccessToken()));
    }
    // ------------- access-refresh-pair -------------

    private void addClaim(JWTCreator.Builder jwt, String name, Object claim) {
        try {
            if (claim instanceof Integer)
                jwt.withClaim(name, (Integer) claim);
            else if (claim instanceof Long)
                jwt.withClaim(name, (Long) claim);
            else if (claim instanceof Double)
                jwt.withClaim(name, (Double) claim);
            else if (claim instanceof Boolean)
                jwt.withClaim(name, (Boolean) claim);
            else if (claim instanceof Date)
                jwt.withClaim(name, (Date) claim);
            else if (claim instanceof List<?>)
                jwt.withClaim(name, (List<?>) claim);
            else if (claim instanceof Map)
                jwt.withClaim(name, (Map) claim);
            else
                jwt.withClaim(name, claim.toString());
        } catch (Exception ex) {
            jwt.withClaim(name, claim.toString());
        }
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public String getAccessTokenEndpoint() {
        return accessTokenEndpoint;
    }

    public String getRefreshTokenEndpoint() {
        return refreshTokenEndpoint;
    }

    public Long getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    public Long getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }



    public TokenDetails getTokenDetailsFromAuthorizationHeader(String authorizationHeader, AppUserService appUserService){
        // TODO refactor this and AuthTokenController
        // get token from header
        String token = authorizationHeader.substring("Bearer ".length());

        // try decode token
        DecodedJWT decodedJWT;
        try {
            decodedJWT = decodeToken(token);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException exception) {
            throw new com.redscooter.exceptions.api.unauthorized.TokenExpiredException(exception);
        } catch (TokenDecodeException exception) {
            throw new InvalidTokenException(exception);
        }

        // check TokenType
        try {
            String tokenType = getTokenType(decodedJWT);
            if (!tokenType.equals(TokenType.access_token.toString()))
                throw new InvalidTokenTypeException(TokenType.access_token.toString(), tokenType);
        } catch (Exception e) {
            if (e instanceof InvalidTokenTypeException)
                throw e;
            throw new InvalidTokenException("Invalid token! Could not read TokenType from token.", e);
        }

        // try get username
        String username;
        try {
            username = getUsernameFromToken(decodedJWT);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token! Could not read username from token.", e);
        }

        // try get authorities
        Collection<SimpleGrantedAuthority> authorities;
        try {
            authorities = getAuthoritiesFromAccessToken(decodedJWT);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token! Could not read authorities from token. Make sure you are using a valid access token.", e);
        }

        // first check if user exists
        AppUser appUser = appUserService.getByUsername(username, false);
        if (appUser==null)
            throw new InvalidTokenUserException(username);
        // check if user is enabled/active and somehow has a token (i.e. the user was deactivated later on)
        if (!appUser.isEnabled())
            throw new UserAccountNotActivatedException();

        return new TokenDetails(appUser, authorities);
    }

}
