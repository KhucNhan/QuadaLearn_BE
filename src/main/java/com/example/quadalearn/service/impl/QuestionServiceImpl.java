// src/main/java/com/example/quadalearn/service/impl/QuestionServiceImpl.java
package com.example.quadalearn.service.impl;

import com.example.quadalearn.model.Question;
import com.example.quadalearn.repository.QuestionRepository;
import com.example.quadalearn.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional
    @Override
    public Question create(Question question) {
        return questionRepository.save(question);
    }

    @Transactional
    @Override
    public Question update(Question question) {
        return questionRepository.save(question);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> findByTestId(Long testId) {
        return questionRepository.findByTest_Id(testId);
    }

    @Override
    public List<Question> findByKnowledgeTag(String tag) {
        return questionRepository.findByKnowledgeTag(tag);
    }

    @Override
    public List<Question> findByType(String type) {
        return questionRepository.findByType(type);
    }
}
