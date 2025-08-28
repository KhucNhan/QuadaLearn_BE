// src/main/java/com/example/quadalearn/repository/CourseRepository.java
package com.example.quadalearn.repository;

import com.example.quadalearn.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByLevel(String level);
}
