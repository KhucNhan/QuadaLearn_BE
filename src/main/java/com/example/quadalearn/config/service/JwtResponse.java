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
    private String avatar;
    private boolean needsCompletion;

    private final Collection<? extends GrantedAuthority> authorities;


    public JwtResponse(Long id, String token, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.authorities = authorities;
    }

    public JwtResponse(Long id, String token, String name, String avatar, Collection<? extends GrantedAuthority> authorities, boolean needsCompletion) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.avatar = avatar;
        this.authorities = authorities;
        this.needsCompletion = needsCompletion;
    }




    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public boolean isNeedsCompletion() {
        return needsCompletion;
    }

    public void setNeedsCompletion(boolean needsCompletion) {
        this.needsCompletion = needsCompletion;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
