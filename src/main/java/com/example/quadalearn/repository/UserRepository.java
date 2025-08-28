// src/main/java/com/example/quadalearn/repository/UserRepository.java
package com.example.quadalearn.repository;

import com.example.quadalearn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
