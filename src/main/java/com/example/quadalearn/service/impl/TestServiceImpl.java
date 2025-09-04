// src/main/java/com/example/quadalearn/service/impl/TestServiceImpl.java
package com.example.quadalearn.service.impl;

import com.example.quadalearn.dto.TestSubmissionRequest;
import com.example.quadalearn.dto.UserAnswerDTO;
import com.example.quadalearn.model.*;
import com.example.quadalearn.repository.QuestionRepository;
import com.example.quadalearn.repository.TestRepository;
import com.example.quadalearn.repository.UserAnswerRepository;
import com.example.quadalearn.repository.UserTestRepository;
import com.example.quadalearn.service.GeminiService;
import com.example.quadalearn.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final UserAnswerRepository userAnswerRepo;
    private final QuestionRepository questionRepo;
    private final GeminiService geminiService;
    private final UserTestRepository userTestRepo;


    @Transactional
    @Override
    public Test create(Test test) {
        return testRepository.save(test);
    }

    @Transactional
    @Override
    public Test update(Test test) {
        return testRepository.save(test);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        testRepository.deleteById(id);
    }

    @Override
    public Optional<Test> findById(Long id) {
        return testRepository.findById(id);
    }

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public List<Test> findByCreator(User creator) {
        return testRepository.findByCreatedBy(creator);
    }

    @Override
    public List<Test> findByType(String type) {
        return testRepository.findByType(type);
    }

    @Transactional
    @Override
    public String submitTest(User user, Long testId, TestSubmissionRequest request) {
        // 1. Tạo bản ghi user_test
        UserTest userTest = UserTest.builder()
                .user(user)
                .test(Test.builder().id(testId).build())
                .score(0.0)
                .build();
        userTestRepo.save(userTest);

        int correctCount = 0;

        // 2. Lưu từng câu trả lời
        for (UserAnswerDTO dto : request.getAnswers()) {
            Question q = questionRepo.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found: " + dto.getQuestionId()));

            boolean isCorrect = q.getAnswerKey().equalsIgnoreCase(dto.getAnswer());
            if (isCorrect) correctCount++;

            UserAnswer ua = UserAnswer.builder()
                    .userTest(userTest)
                    .question(q)
                    .answer(dto.getAnswer())
                    .isCorrect(isCorrect)
                    .build();

            userAnswerRepo.save(ua);
        }

        // 3. Tính điểm
        double score = (double) correctCount / request.getAnswers().size() * 100.0;
        userTest.setScore(score);
        userTestRepo.save(userTest);

        // 4. Chuẩn bị JSON gửi AI
        List<Map<String, String>> answersJson = request.getAnswers().stream().map(dto -> {
            Question q = questionRepo.findById(dto.getQuestionId()).orElseThrow();

            Map<String, String> map = new HashMap<>();
            map.put("question", q.getContent());
            map.put("correct_answer", q.getAnswerKey());
            map.put("user_answer", dto.getAnswer() != null ? dto.getAnswer() : "");
            map.put("knowledgeTag", q.getKnowledgeTag() != null ? q.getKnowledgeTag() : "");

            return map;
        }).toList();


        // 5. Gửi sang Gemini để phân tích
        try {
            return geminiService.analyzeTest(request.getAim(), answersJson, score);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
