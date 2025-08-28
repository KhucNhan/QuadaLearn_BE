// src/main/java/com/example/quadalearn/service/UserTestService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.UserTest;

import java.util.List;
import java.util.Optional;

public interface UserTestService {
    UserTest create(UserTest userTest);
    UserTest update(UserTest userTest);
    void delete(Long id);

    Optional<UserTest> findById(Long id);
    List<UserTest> findAll();
    List<UserTest> findByUserId(Long userId);
    List<UserTest> findByTestId(Long testId);
    Optional<UserTest> findLatestByUserId(Long userId);
}
