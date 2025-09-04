// src/main/java/com/example/quadalearn/repository/LessonRepository.java
package com.example.quadalearn.repository.learning;

import com.example.quadalearn.model.learning.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse_Id(Long courseId);
    List<Lesson> findByKnowledgeTag(String knowledgeTag);
}
