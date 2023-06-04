package com.redscooter.security.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedTokenDetailsDTO {
    String access_token;
    String refresh_token;
    String username;
    Long expires_at;
    List<String> authorities;

    public DetailedTokenDetailsDTO(TokenPair tokenPair, Long expires_at, List<String> authorities, String username) {
        setUsername(username);
        setAccess_token(tokenPair.getAccessToken());
        setRefresh_token(tokenPair.getRefreshToken());
        setExpires_at(expires_at);
        setAuthorities(authorities);
    }
}

