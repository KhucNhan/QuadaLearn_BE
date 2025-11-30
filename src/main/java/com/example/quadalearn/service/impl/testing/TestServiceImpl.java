package com.example.quadalearn.service.impl.testing;

import com.example.quadalearn.dto.TestSubmissionRequest;
import com.example.quadalearn.dto.user.UserAnswerDTO;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.testing.Question;
import com.example.quadalearn.model.testing.Test;
import com.example.quadalearn.model.testing.UserAnswer;
import com.example.quadalearn.model.testing.UserTest;
import com.example.quadalearn.repository.auth.IUserRepository;
import com.example.quadalearn.repository.testing.QuestionRepository;
import com.example.quadalearn.repository.testing.TestRepository;
import com.example.quadalearn.repository.testing.UserAnswerRepository;
import com.example.quadalearn.repository.testing.UserTestRepository;
import com.example.quadalearn.service.impl.GeminiService;
import com.example.quadalearn.service.testing.ITestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements ITestService {
    private final TestRepository testRepository;
    private final UserAnswerRepository userAnswerRepo;
    private final QuestionRepository questionRepo;
    private final GeminiService geminiService;
    private final UserTestRepository userTestRepo;
    private final IUserRepository userRepository;


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
    public Map<String, Object> submitTestAndGetAnalysis(User user, Long testId, TestSubmissionRequest request) {
        // 1. Lấy test
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        // 2. Lấy danh sách câu hỏi
        List<Question> questions = questionRepo.findByTest_Id(testId);

        // 3. Tạo UserTest (CHƯA SAVE)
        UserTest userTest = UserTest.builder()
                .user(user)
                .test(test)
                .score(0.0)
                .time(LocalDateTime.now())
                .timeSpent(request.getTimeSpent())
                .build();

        // 4. Xử lý từng câu trả lời và tính điểm
        List<UserAnswer> userAnswers = new ArrayList<>();
        List<Map<String, String>> answersForAI = new ArrayList<>();
        int correctCount = 0;

        for (var answerDTO : request.getAnswers()) {
            Question question = questions.stream()
                    .filter(q -> q.getId().equals(answerDTO.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Question not found: " + answerDTO.getQuestionId()));

            // Check đúng/sai
            String userAnswerText = answerDTO.getAnswer();
            boolean isCorrect = checkAnswer(question, userAnswerText);

            if (isCorrect) correctCount++;

            // Tạo UserAnswer (liên kết với userTest)
            UserAnswer userAnswer = UserAnswer.builder()
                    .userTest(userTest)
                    .question(question)
                    .answer(userAnswerText)
                    .isCorrect(isCorrect)
                    .build();

            userAnswers.add(userAnswer);

            // Chuẩn bị data cho AI
            Map<String, String> answerMap = new HashMap<>();
            answerMap.put("question", question.getContent());
            answerMap.put("correct_answer", question.getAnswerKey());
            answerMap.put("user_answer", userAnswerText != null ? userAnswerText : "");
            answerMap.put("knowledgeTag", question.getKnowledgeTag() != null ? question.getKnowledgeTag() : "");
            answersForAI.add(answerMap);
        }

        // 5. Tính điểm
        double score = questions.isEmpty() ? 0.0 :
                (double) correctCount / questions.size() * 100.0;
        userTest.setScore(score);

        // 6. Set UserAnswers vào UserTest
        userTest.setUserAnswers(userAnswers);

        // 7. ✅ SAVE DUY NHẤT 1 LẦN (cascade sẽ tự động save userAnswers)
        UserTest savedUserTest = userTestRepo.save(userTest);

        // 8. Gọi AI phân tích
        String analysis;
        try {
            analysis = geminiService.analyzeTest(request.getAim(), answersForAI, score);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get AI analysis: " + e.getMessage());
        }

        // 9. Trả về kết quả
        return Map.of(
                "testId", testId,
                "userTestId", savedUserTest.getId(),
                "score", score,
                "correctCount", correctCount,
                "totalQuestions", questions.size(),
                "scoreAnalysis", analysis
        );
    }

    private boolean checkAnswer(Question question, String userAnswer) {
        if (userAnswer == null || question.getAnswerKey() == null) {
            return false;
        }

        // Lấy đáp án đúng dựa vào answerKey
        String correctAnswer = null;
        String key = question.getAnswerKey().toUpperCase().trim();

        switch (key) {
            case "A":
                correctAnswer = question.getA();
                break;
            case "B":
                correctAnswer = question.getB();
                break;
            case "C":
                correctAnswer = question.getC();
                break;
            case "D":
                correctAnswer = question.getD();
                break;
            default:
                return false;
        }

        if (correctAnswer == null) {
            return false;
        }

        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }
}
