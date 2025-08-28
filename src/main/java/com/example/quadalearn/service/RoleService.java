// src/main/java/com/example/quadalearn/service/RoleService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role create(Role role);
    Role update(Role role);
    void delete(Long id);

    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    List<Role> findAll();
}
