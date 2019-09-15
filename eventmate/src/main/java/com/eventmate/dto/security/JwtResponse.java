package com.eventmate.dto.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> authorities;


    public JwtResponse(String accessToken, Collection<? extends GrantedAuthority> authorities, String username) {
        this.token = accessToken;
        this.authorities = authorities;
        this.username = username;
    }
}
