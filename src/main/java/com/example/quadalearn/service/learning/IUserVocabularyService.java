// src/main/java/com/example/quadalearn/service/UserVocabularyService.java
package com.example.quadalearn.service.learning;

import com.example.quadalearn.model.learning.UserVocabulary;

import java.util.List;
import java.util.Optional;

public interface IUserVocabularyService {
    UserVocabulary create(UserVocabulary userVocabulary);
    UserVocabulary update(UserVocabulary userVocabulary);
    void delete(Long id);

    Optional<UserVocabulary> findById(Long id);
    List<UserVocabulary> findAll();
    List<UserVocabulary> findByUserId(Long userId);
    boolean exists(Long userId, Long vocabularyId);
}
