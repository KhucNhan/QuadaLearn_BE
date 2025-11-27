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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public Double calculateScore(Long testId, TestSubmissionRequest request) {
        // Lấy tất cả câu hỏi của test
        List<Question> questions = questionRepo.findByTest_Id(testId);

        if (questions.isEmpty()) {
            return 0.0;
        }

        // Đếm số câu trả lời đúng
        int correctCount = 0;

        for (UserAnswerDTO answer : request.getAnswers()) {
            // Tìm câu hỏi tương ứng
            Question question = questions.stream()
                    .filter(q -> q.getId().equals(answer.getQuestionId()))
                    .findFirst()
                    .orElse(null);

            if (question != null && answer.getAnswer() != null) {
                // Kiểm tra đáp án có đúng không
                if (isCorrectAnswer(question, answer.getAnswer())) {
                    correctCount++;
                }
            }
        }

        // Tính điểm theo % (0-100)
        double score = ((double) correctCount / questions.size()) * 100;

        // Làm tròn 2 chữ số thập phân
        return Math.round(score * 100.0) / 100.0;
    }

    /**
     * ✅ Helper method: Kiểm tra đáp án có đúng không
     */
    private boolean isCorrectAnswer(Question question, String userAnswer) {
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

        // So sánh (không phân biệt HOA/thường, trim khoảng trắng)
        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }
}
