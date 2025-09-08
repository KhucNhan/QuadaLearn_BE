// src/main/java/com/example/quadalearn/repository/UserProgressRepository.java
package com.example.quadalearn.repository.progress;

import com.example.quadalearn.model.progress.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByUser_Id(Long userId);
    List<UserProgress> findByCourse_Id(Long courseId);
    Optional<UserProgress> findByUser_IdAndCourse_Id(Long userId, Long courseId);
}
