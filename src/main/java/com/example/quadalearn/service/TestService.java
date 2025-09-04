// src/main/java/com/example/quadalearn/service/TestService.java
package com.example.quadalearn.service;

import com.example.quadalearn.dto.TestSubmissionRequest;
import com.example.quadalearn.model.Test;
import com.example.quadalearn.model.User;

import java.util.List;
import java.util.Optional;

public interface TestService {
    Test create(Test test);
    Test update(Test test);
    void delete(Long id);

    Optional<Test> findById(Long id);
    List<Test> findAll();
    List<Test> findByCreator(User creator);
    List<Test> findByType(String type);

    String submitTest(User user, Long testId, TestSubmissionRequest request);
}
