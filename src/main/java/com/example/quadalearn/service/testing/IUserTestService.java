// src/main/java/com/example/quadalearn/service/UserTestService.java
package com.example.quadalearn.service.testing;

import com.example.quadalearn.dto.TestSubmissionRequest;
import com.example.quadalearn.dto.user.test.TestHistoryDTO;
import com.example.quadalearn.dto.user.test.TestHistoryDetailDTO;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.testing.Test;
import com.example.quadalearn.model.testing.UserTest;

import java.util.List;
import java.util.Optional;

public interface IUserTestService {
    public List<TestHistoryDTO> getTestHistory(Long id);
    public TestHistoryDetailDTO getTestDetail(Long userTestId);
    public UserTest saveTestResult(User user, Test test, TestSubmissionRequest request, Double score);
}
