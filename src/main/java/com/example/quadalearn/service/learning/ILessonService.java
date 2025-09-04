// src/main/java/com/example/quadalearn/service/LessonService.java
package com.example.quadalearn.service.learning;

import com.example.quadalearn.model.learning.Lesson;

import java.util.List;
import java.util.Optional;

public interface ILessonService {
    Lesson create(Lesson lesson);
    Lesson update(Lesson lesson);
    void delete(Long id);

    Optional<Lesson> findById(Long id);
    List<Lesson> findAll();
    List<Lesson> findByCourseId(Long courseId);
    List<Lesson> findByKnowledgeTag(String tag);
}
