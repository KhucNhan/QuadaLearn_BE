// src/main/java/com/example/quadalearn/service/QuestionService.java
package com.example.quadalearn.service.testing;

import com.example.quadalearn.model.testing.Question;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    Question create(Question question);
    Question update(Question question);
    void delete(Long id);

    Optional<Question> findById(Long id);
    List<Question> findAll();
    List<Question> findByTestId(Long testId);
    List<Question> findByKnowledgeTag(String tag);
    List<Question> findByType(String type);
}
