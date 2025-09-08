// src/main/java/com/example/quadalearn/repository/QuestionRepository.java
package com.example.quadalearn.repository.testing;

import com.example.quadalearn.model.testing.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTest_Id(Long testId);
    List<Question> findByKnowledgeTag(String knowledgeTag);
    List<Question> findByType(String type);
}
