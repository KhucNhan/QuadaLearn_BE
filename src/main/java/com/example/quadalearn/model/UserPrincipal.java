package com.example.quadalearn.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Getter
public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> roles;

    public UserPrincipal(String email, String password,
                         Collection<? extends GrantedAuthority> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public static UserPrincipal build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new UserPrincipal(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // Dùng email làm username
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // nếu muốn quản lý thì thêm field vào User
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // tương tự
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
