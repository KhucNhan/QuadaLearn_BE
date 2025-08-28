// src/main/java/com/example/quadalearn/service/impl/UserAnswerServiceImpl.java
package com.example.quadalearn.service.impl;

import com.example.quadalearn.model.UserAnswer;
import com.example.quadalearn.repository.UserAnswerRepository;
import com.example.quadalearn.service.UserAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAnswerServiceImpl implements UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    @Transactional
    @Override
    public UserAnswer create(UserAnswer userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }

    @Transactional
    @Override
    public UserAnswer update(UserAnswer userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userAnswerRepository.deleteById(id);
    }

    @Override
    public Optional<UserAnswer> findById(Long id) {
        return userAnswerRepository.findById(id);
    }

    @Override
    public List<UserAnswer> findAll() {
        return userAnswerRepository.findAll();
    }

    @Override
    public List<UserAnswer> findByUserTestId(Long userTestId) {
        return userAnswerRepository.findByUserTest_Id(userTestId);
    }

    @Override
    public List<UserAnswer> findByQuestionId(Long questionId) {
        return userAnswerRepository.findByQuestion_Id(questionId);
    }
}
