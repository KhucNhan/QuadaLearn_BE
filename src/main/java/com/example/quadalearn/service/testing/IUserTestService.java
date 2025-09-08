// src/main/java/com/example/quadalearn/service/UserTestService.java
package com.example.quadalearn.service.testing;

import com.example.quadalearn.model.testing.UserTest;

import java.util.List;
import java.util.Optional;

public interface IUserTestService {
    UserTest create(UserTest userTest);
    UserTest update(UserTest userTest);
    void delete(Long id);

    Optional<UserTest> findById(Long id);
    List<UserTest> findAll();
    List<UserTest> findByUserId(Long userId);
    List<UserTest> findByTestId(Long testId);
    Optional<UserTest> findLatestByUserId(Long userId);
}
