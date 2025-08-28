// src/main/java/com/example/quadalearn/service/VocabularyService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.Vocabulary;

import java.util.List;
import java.util.Optional;

public interface VocabularyService {
    Vocabulary create(Vocabulary vocabulary);
    Vocabulary update(Vocabulary vocabulary);
    void delete(Long id);

    Optional<Vocabulary> findById(Long id);
    List<Vocabulary> findAll();
    List<Vocabulary> findByLevel(String level);
    List<Vocabulary> searchByWord(String keyword);
}
