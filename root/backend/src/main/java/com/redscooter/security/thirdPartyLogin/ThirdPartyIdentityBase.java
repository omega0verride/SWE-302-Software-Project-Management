package com.redscooter.security.thirdPartyLogin;

import com.redscooter.API.appUser.AppUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Hashtable;

@Slf4j
public abstract class ThirdPartyIdentityBase {

    private static final RestTemplate restTemplate = new RestTemplate();
    private String URL;
    private String userIdJSONPath;
    private String appUserPersistIdPrefix;
    private String invalidAccessTokenPattern;

    private static final Hashtable<String, Class<?>> registeredIdPrefixes = new Hashtable<>();

    public ThirdPartyIdentityBase(String URL, String userIdJSONPath, String appUserPersistIdPrefix, String invalidAccessTokenPattern) {
        setURL(URL);
        setUserIdJSONPath(userIdJSONPath);
        setAppUserPersistIdPrefix(appUserPersistIdPrefix);
        setInvalidAccessTokenPattern(invalidAccessTokenPattern);
    }

    public AppUser getAppUserFromToken(String token) {
        if (token == null || token.trim().length() == 0)
            throw new ThirdPartyIdentityProviderException("Invalid Token. Token cannot be null or empty.", "InvalidToken");
        AppUser appUser = new AppUser();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(URL + token, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            appUser.setUsername(appUserPersistIdPrefix + tryGetValue(root, userIdJSONPath));
            mapExtraUserDetails(appUser, root);
            appUser.setPassword(MultiAuthIdentityProvider.getThirdPartyAccountPassword());
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.BAD_REQUEST) || (invalidAccessTokenPattern != null && ex.getMessage() != null && ex.getMessage().matches(invalidAccessTokenPattern))) {
                throw new ThirdPartyIdentityProviderException("Invalid Token.", "InvalidToken", ex);
            }
            throw new ThirdPartyIdentityProviderException("Invalid HTTPResponse from Identity Provider API.", true, ex);
        } catch (RestClientException ex) {
            throw new ThirdPartyIdentityProviderException("RestClientException", true, ex);
        } catch (JsonMappingException e) {
            throw new ThirdPartyIdentityProviderException("Could not read JSON response. JsonMappingException", true, e);
        } catch (JsonProcessingException e) {
            throw new ThirdPartyIdentityProviderException("Could not read JSON response. JsonProcessingException", true, e);
        }

        return appUser;
    }

    protected abstract void mapExtraUserDetails(AppUser appUser, JsonNode root);

    protected static String tryGetValue(JsonNode root, String key) {
        return tryGetValue(root, key, true);
    }

    protected static String tryGetValue(JsonNode root, String key, boolean throwException) {
        try {
            return root.get(key).asText();
        } catch (Exception ex) {
            ThirdPartyIdentityProviderException thirdPartyIdentityProviderException = new ThirdPartyIdentityProviderException("Could not get the value of $.\"" + key + "\". Please report this to the development team.", true, ex);
            if (throwException)
                throw thirdPartyIdentityProviderException;
            else
                log.error(thirdPartyIdentityProviderException.toString(), thirdPartyIdentityProviderException);
            return null;
        }
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        if (URL == null || URL.trim().length() == 0)
            throw new IllegalArgumentException("URL cannot be null or empty");
        this.URL = URL;
    }

    public String getUserIdJSONPath() {
        return userIdJSONPath;
    }

    public void setUserIdJSONPath(String userIdJSONPath) {
        if (userIdJSONPath == null || userIdJSONPath.trim().length() == 0)
            throw new IllegalArgumentException("userIdJSONPath cannot be null or empty");
        this.userIdJSONPath = userIdJSONPath;
    }

    public String getAppUserPersistIdPrefix() {
        return appUserPersistIdPrefix;
    }

    public void setAppUserPersistIdPrefix(String appUserPersistIdPrefix) {
        if (appUserPersistIdPrefix == null || appUserPersistIdPrefix.trim().length() == 0)
            throw new IllegalArgumentException("appUserPersistIdPrefix cannot be null or empty");
        if (appUserPersistIdPrefix.equals(getAppUserPersistIdPrefix()))
            return;
        if (registeredIdPrefixes.containsKey(appUserPersistIdPrefix)) {
            if (!registeredIdPrefixes.get(appUserPersistIdPrefix).equals(getClass()))
                throw new IllegalArgumentException("appUserPersistIdPrefix:'" + appUserPersistIdPrefix + "' already exists in " + registeredIdPrefixes);
        }
        if (getAppUserPersistIdPrefix() != null)
            registeredIdPrefixes.remove(getAppUserPersistIdPrefix());
        this.appUserPersistIdPrefix = appUserPersistIdPrefix;
        registeredIdPrefixes.put(getAppUserPersistIdPrefix(), getClass());
    }

    public String getInvalidAccessTokenPattern() {
        return invalidAccessTokenPattern;
    }

    public void setInvalidAccessTokenPattern(String invalidAccessTokenPattern) {
        this.invalidAccessTokenPattern = invalidAccessTokenPattern;
    }
}
