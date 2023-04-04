package com.redscooter.security;

import com.redscooter.API.appUser.AppUserService;
import com.redscooter.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public static final SimpleGrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority("ROLE_ADMIN");
    public static final SimpleGrantedAuthority ANONYMOUS_AUTHORITY = new SimpleGrantedAuthority("ROLE_ANONYMOUS");

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
        }catch (Exception ex){
            return false;
        }
    }
}
