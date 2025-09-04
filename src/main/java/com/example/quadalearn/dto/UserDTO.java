package com.example.quadalearn.dto;

import com.example.quadalearn.model.auth.Role;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String email;
    private Set<Role> roles;


    public UserDTO() {
    }


    public UserDTO(Long id, String email, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

