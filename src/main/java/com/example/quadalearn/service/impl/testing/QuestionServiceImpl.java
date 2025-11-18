// src/main/java/com/example/quadalearn/service/impl/QuestionServiceImpl.java
package com.example.quadalearn.service.impl.testing;

import com.example.quadalearn.dto.course.question.QuestionDTO;
import com.example.quadalearn.model.testing.Question;
import com.example.quadalearn.repository.testing.QuestionRepository;
import com.example.quadalearn.service.testing.IQuestionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements IQuestionService {
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
    public List<QuestionDTO> findByKnowledgeTag(String knowledgeTag) {
        List<Question> questions = questionRepository.findByKnowledgeTag(knowledgeTag);

        return questions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private QuestionDTO convertToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setContent(question.getContent());
        dto.setType(question.getType());
        dto.setAnswerKey(question.getAnswerKey());
        dto.setKnowledgeTag(question.getKnowledgeTag());
        dto.setTestId(question.getTest() != null ? question.getTest().getId() : null);

        // Tạo list options từ a, b, c, d
        List<String> options = new ArrayList<>();
        if (question.getA() != null) options.add(question.getA());
        if (question.getB() != null) options.add(question.getB());
        if (question.getC() != null) options.add(question.getC());
        if (question.getD() != null) options.add(question.getD());

        dto.setOptions(options);

        return dto;
    }

    @Override
    public List<Question> findByType(String type) {
        return questionRepository.findByType(type);
    }
}
