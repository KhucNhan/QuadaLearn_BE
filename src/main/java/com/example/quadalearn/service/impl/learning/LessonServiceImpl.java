// src/main/java/com/example/quadalearn/service/impl/LessonServiceImpl.java
package com.example.quadalearn.service.impl.learning;

import com.example.quadalearn.model.learning.Lesson;
import com.example.quadalearn.repository.learning.LessonRepository;
import com.example.quadalearn.service.learning.ILessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements ILessonService {
    private final LessonRepository lessonRepository;

    @Transactional
    @Override
    public Lesson create(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public Lesson update(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> findByCourseId(Long courseId) {
        return lessonRepository.findByCourse_Id(courseId);
    }

    @Override
    public List<Lesson> findByKnowledgeTag(String tag) {
        return lessonRepository.findByKnowledgeTag(tag);
    }
}
