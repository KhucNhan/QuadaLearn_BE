// src/main/java/com/example/quadalearn/repository/TestRepository.java
package com.example.quadalearn.repository.testing;

import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.testing.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByCreatedBy(User user);
    List<Test> findByType(String type);
}
