// src/main/java/com/example/quadalearn/repository/UserAnswerRepository.java
package com.example.quadalearn.repository.testing;

import com.example.quadalearn.model.testing.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByUserTest_Id(Long userTestId);
    List<UserAnswer> findByQuestion_Id(Long questionId);
}
