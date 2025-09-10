// src/main/java/com/example/quadalearn/service/impl/CourseServiceImpl.java
package com.example.quadalearn.service.impl.learning;

import com.example.quadalearn.model.learning.Course;
import com.example.quadalearn.repository.learning.CourseRepository;
import com.example.quadalearn.service.learning.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {
    private final CourseRepository courseRepository;

    @Transactional
    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    @Override
    public Course update(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findByLevel(String level) {
        return courseRepository.findByLevel(level);
    }

    @Override
    public Optional<Course> findCourseWithLessonsById(Long courseId) {
        return courseRepository.findCourseWithLessonsById(courseId);
    }
}
