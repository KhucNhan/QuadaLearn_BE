// src/main/java/com/example/quadalearn/service/UserVocabularyService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.UserVocabulary;

import java.util.List;
import java.util.Optional;

public interface UserVocabularyService {
    UserVocabulary create(UserVocabulary userVocabulary);
    UserVocabulary update(UserVocabulary userVocabulary);
    void delete(Long id);

    Optional<UserVocabulary> findById(Long id);
    List<UserVocabulary> findAll();
    List<UserVocabulary> findByUserId(Long userId);
    boolean exists(Long userId, Long vocabularyId);
}
