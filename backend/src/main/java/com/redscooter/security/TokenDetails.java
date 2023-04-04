package com.redscooter.security;

import com.redscooter.API.appUser.AppUser;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Getter
public class TokenDetails {
    AppUser appUser;
    Collection<SimpleGrantedAuthority> authorities;

    public TokenDetails(AppUser appUser, Collection<SimpleGrantedAuthority> authorities) {
        this.appUser=appUser;
        this.authorities=authorities;
    }
}
