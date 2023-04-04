package com.redscooter.security.thirdPartyLogin;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.appUser.AppUserService;
import com.redscooter.exceptions.api.ResourceNotFoundException;
import com.redscooter.exceptions.api.unauthorized.InvalidCredentialsException;
import com.redscooter.exceptions.api.unauthorized.UserAccountNotActivatedException;
import com.redscooter.exceptions.to_refactor.InvalidValueException;
import com.redscooter.security.DTO.MultiAuthIdentityProviderDTO;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class MultiAuthIdentityProvider {

    // TODO[production] move to ENV VARIABLES
    @Getter
    private static final String ThirdPartyAccountPassword = "Eltl$@Q@K1iWQ4EoGOlm!TtLWu0y&Iux9ELr7ST9dU&vnsSYuK";

    @Autowired
    GoogleIdentity googleIdentity;
    @Autowired
    FacebookIdentity facebookIdentity;

    public UsernamePasswordAuthenticationToken getAuthenticationTokenFromAuthDetails(MultiAuthIdentityProviderDTO authDetails, AppUserService appUserService) {
        AppUser appUser = null;
        String password = getThirdPartyAccountPassword();
        if (authDetails.getAuthType() == AuthType.BASIC) {
            if (authDetails.getUsername() == null || authDetails.getUsername().trim().length() == 0) {
                throw new InvalidValueException("Username cannot be null or empty when AuthType=BASIC!");
            }
            if (authDetails.getPassword() == null || authDetails.getPassword().trim().length() == 0) {
                throw new InvalidValueException("Username cannot be null or empty when AuthType=BASIC!");
            }
            appUser = appUserService.getUser(authDetails.getUsername()).orElseThrow(() -> {
                throw new InvalidCredentialsException(new ResourceNotFoundException("User", "username", authDetails.getUsername()));
            });
            if (!appUser.getUserAuthType().equals(AuthType.BASIC)) { // this is achieved only if someone tries to access a non BASIC account, the condition prevents accessing the third party provider accounts that have a common password
                throw new InvalidCredentialsException(new ResourceNotFoundException("User", "username", authDetails.getUsername()));
            }
            password = authDetails.getPassword();
        } else if (authDetails.getAuthType() == AuthType.GOOGLE) {
            appUser = appUserService.getOrRegisterUserInternally(googleIdentity.getAppUserFromToken(authDetails.getToken()));
        } else if (authDetails.getAuthType() == AuthType.INSTAGRAM) {
            throw new NotImplementedException("Instagram login is not implemented yet!");
//            appUser = appUserService.getOrRegisterUserInternally(facebookIdentity.getAppUserFromToken(authDetails.getToken()));
        } else if (authDetails.getAuthType() == AuthType.FACEBOOK) {
            appUser = appUserService.getOrRegisterUserInternally(facebookIdentity.getAppUserFromToken(authDetails.getToken()));
        }

        if (appUser == null) {
            throw new InvalidCredentialsException("Could not resolve user from provided authentication details.");
        }
        if (!appUser.isEnabled())
            throw new UserAccountNotActivatedException();
        return new UsernamePasswordAuthenticationToken(appUser.getUsername(), password);
    }
}
