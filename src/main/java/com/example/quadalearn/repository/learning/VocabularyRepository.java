// src/main/java/com/example/quadalearn/repository/VocabularyRepository.java
package com.example.quadalearn.repository.learning;

import com.example.quadalearn.model.learning.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findByLevel(String level);
    List<Vocabulary> findByWordContainingIgnoreCase(String keyword);
}
