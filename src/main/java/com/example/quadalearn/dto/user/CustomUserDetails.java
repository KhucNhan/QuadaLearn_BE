package com.example.quadalearn.dto.user;

import com.example.quadalearn.model.auth.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ✅ THAY THẾ logic trả về list rỗng bằng logic lấy Roles từ Entity User
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // lấy password từ entity
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // dùng email để login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // có thể thêm field trong User để check
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // có thể thêm field trong User để check
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // có thể thêm field trong User để check
    }

    @Override
    public boolean isEnabled() {
        return true; // có thể thêm field trong User để check
    }
}
