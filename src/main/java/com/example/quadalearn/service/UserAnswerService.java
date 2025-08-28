// src/main/java/com/example/quadalearn/service/UserAnswerService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.UserAnswer;

import java.util.List;
import java.util.Optional;

public interface UserAnswerService {
    UserAnswer create(UserAnswer userAnswer);
    UserAnswer update(UserAnswer userAnswer);
    void delete(Long id);

    Optional<UserAnswer> findById(Long id);
    List<UserAnswer> findAll();
    List<UserAnswer> findByUserTestId(Long userTestId);
    List<UserAnswer> findByQuestionId(Long questionId);
}
