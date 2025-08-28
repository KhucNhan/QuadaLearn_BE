// src/main/java/com/example/quadalearn/service/UserService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    User update(User user);
    void delete(Long id);

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();

    UserDetails loadUserByUsername(String email);
}
