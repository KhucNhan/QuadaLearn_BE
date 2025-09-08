package com.example.quadalearn.config.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@Getter
@Setter
public class JwtResponse {

    private Long id;
    private String token;
    private String type = "Bearer";
    private String name;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(Long id, String token, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.authorities = authorities;
    }
}
