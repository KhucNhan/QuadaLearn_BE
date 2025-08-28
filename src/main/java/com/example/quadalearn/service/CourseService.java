// src/main/java/com/example/quadalearn/service/CourseService.java
package com.example.quadalearn.service;

import com.example.quadalearn.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course create(Course course);
    Course update(Course course);
    void delete(Long id);

    Optional<Course> findById(Long id);
    List<Course> findAll();
    List<Course> findByLevel(String level);
}
