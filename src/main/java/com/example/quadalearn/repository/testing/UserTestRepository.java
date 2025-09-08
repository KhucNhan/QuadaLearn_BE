// src/main/java/com/example/quadalearn/repository/UserTestRepository.java
package com.example.quadalearn.repository.testing;

import com.example.quadalearn.model.testing.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    List<UserTest> findByUser_Id(Long userId);
    List<UserTest> findByTest_Id(Long testId);
    Optional<UserTest> findTop1ByUser_IdOrderByIdDesc(Long userId);
}
