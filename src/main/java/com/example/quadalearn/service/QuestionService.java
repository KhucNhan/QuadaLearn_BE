// src/main/java/com/example/quadalearn/service/QuestionService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question create(Question question);
    Question update(Question question);
    void delete(Long id);

    Optional<Question> findById(Long id);
    List<Question> findAll();
    List<Question> findByTestId(Long testId);
    List<Question> findByKnowledgeTag(String tag);
    List<Question> findByType(String type);
}
