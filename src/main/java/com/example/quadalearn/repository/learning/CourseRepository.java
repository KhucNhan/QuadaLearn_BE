// src/main/java/com/example/quadalearn/repository/CourseRepository.java
package com.example.quadalearn.repository.learning;

import com.example.quadalearn.model.learning.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByLevel(String level);

    @Query(value = "SELECT * FROM courses LIMIT 6", nativeQuery = true)
    List<Course> findTop6Courses();

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.lessons WHERE c.id = :courseId")
    Optional<Course> findCourseWithLessonsById(@Param("courseId") Long courseId);
}
