// src/main/java/com/example/quadalearn/repository/CourseRepository.java
package com.example.quadalearn.repository.learning;

import com.example.quadalearn.model.learning.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByLevel(String level);

    @Query(value = "SELECT * FROM courses LIMIT 6", nativeQuery = true)
    List<Course> findTop6Courses();
}
