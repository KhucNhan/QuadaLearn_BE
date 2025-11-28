// src/main/java/com/example/quadalearn/repository/UserTestRepository.java
package com.example.quadalearn.repository.testing;

import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.testing.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    List<UserTest> findByUser_Id(Long userId);
    List<UserTest> findByTest_Id(Long testId);
    Optional<UserTest> findTop1ByUser_IdOrderByIdDesc(Long userId);
    List<UserTest> findByUserOrderByTimeDesc(User user);

    // Lấy chi tiết 1 bài test kèm theo user answers
    @Query("SELECT ut FROM UserTest ut " +
            "LEFT JOIN FETCH ut.userAnswers ua " +
            "LEFT JOIN FETCH ua.question " +
            "WHERE ut.id = :id")
    Optional<UserTest> findByIdWithAnswers(@Param("id") Long id);
}
