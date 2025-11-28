// src/main/java/com/example/quadalearn/service/TestService.java
package com.example.quadalearn.service.testing;

import com.example.quadalearn.dto.TestSubmissionRequest;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.testing.Test;

import java.util.List;
import java.util.Optional;

public interface ITestService {
    Test create(Test test);
    Test update(Test test);
    void delete(Long id);

    Optional<Test> findById(Long id);
    List<Test> findAll();
    List<Test> findByCreator(User creator);
    List<Test> findByType(String type);

    String submitTest(User user, Long testId, TestSubmissionRequest request);
    Double calculateScore(Long testId, TestSubmissionRequest request);
}
