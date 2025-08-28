// src/main/java/com/example/quadalearn/repository/UserVocabularyRepository.java
package com.example.quadalearn.repository;

import com.example.quadalearn.model.UserVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVocabularyRepository extends JpaRepository<UserVocabulary, Long> {
    List<UserVocabulary> findByUser_Id(Long userId);
    boolean existsByUser_IdAndVocabulary_Id(Long userId, Long vocabularyId);
}
