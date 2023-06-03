package com.redscooter.security;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.appUser.AppUserService;
import com.redscooter.exceptions.api.forbidden.ForbiddenException;
import com.redscooter.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFacade {

    public static final SimpleGrantedAuthority ANONYMOUS_AUTHORITY = new SimpleGrantedAuthority("ROLE_ANONYMOUS");
    public static final SimpleGrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority("ROLE_USER");
    public static final SimpleGrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority("ROLE_ADMIN");

    public static Authentication getCurrentSecurityContextAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAdminOnCurrentSecurityContext() {
        return Utilities.notNullAndContains(getCurrentSecurityContextAuthentication().getAuthorities(), ADMIN_AUTHORITY);
    }

    public static boolean isAdminOrCurrentUserOnCurrentSecurityContext(String username) {
        Authentication authentication = getCurrentSecurityContextAuthentication();
        return Utilities.notNullAndContains(authentication.getAuthorities(), ADMIN_AUTHORITY) || (Utilities.notNullAndContains(authentication.getAuthorities(), ANONYMOUS_AUTHORITY) && authentication.getPrincipal().equals(username));
    }

    public static boolean hasAuthority(SimpleGrantedAuthority authority) {
        return getCurrentSecurityContextAuthentication().getAuthorities().contains(authority);
    }

    @Autowired
    public static boolean isAdminAuthorization(String authorizationHeader, JwtUtils jwtUtils, AppUserService appUserService) {
        try {
            return Utilities.notNullAndContains(jwtUtils.getTokenDetailsFromAuthorizationHeader(authorizationHeader, appUserService).getAuthorities(), ADMIN_AUTHORITY);
        } catch (Exception ex) {
            return false;
        }
    }

    public static void ensureAdmin() {
        if (!isAdminOnCurrentSecurityContext())
            throw new ForbiddenException();
    }

    public static void ensureAdminOrCurrentUserOnCurrentSecurityContext(String username) {
        if (!isAdminOrCurrentUserOnCurrentSecurityContext(username))
            throw new ForbiddenException("The authorization token provided cannot access resources for user: ["+username+"]. The token of the user itself or an admin user must be used.");
    }

    public static boolean isAdminOrCurrentUserOnCurrentSecurityContext(AppUser appUser) {
        if (appUser==null)
            return isAdminOnCurrentSecurityContext();
        return isAdminOrCurrentUserOnCurrentSecurityContext(appUser.getUsername());
    }
}
