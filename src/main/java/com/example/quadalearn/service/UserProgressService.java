// src/main/java/com/example/quadalearn/service/UserProgressService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.UserProgress;

import java.util.List;
import java.util.Optional;

public interface UserProgressService {
    UserProgress create(UserProgress userProgress);
    UserProgress update(UserProgress userProgress);
    void delete(Long id);

    Optional<UserProgress> findById(Long id);
    List<UserProgress> findAll();
    List<UserProgress> findByUserId(Long userId);
    List<UserProgress> findByCourseId(Long courseId);
    Optional<UserProgress> findByUserIdAndCourseId(Long userId, Long courseId);
}
