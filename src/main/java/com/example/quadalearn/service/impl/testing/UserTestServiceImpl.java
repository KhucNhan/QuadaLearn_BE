package com.example.quadalearn.service.impl.testing;

import com.example.quadalearn.dto.*;
import com.example.quadalearn.dto.user.UserAnswerDetailDTO;
import com.example.quadalearn.dto.user.test.QuestionDetailDTO;
import com.example.quadalearn.dto.user.test.TestHistoryDTO;
import com.example.quadalearn.dto.user.test.TestHistoryDetailDTO;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.testing.*;
import com.example.quadalearn.repository.auth.IUserRepository;
import com.example.quadalearn.repository.testing.QuestionRepository;
import com.example.quadalearn.repository.testing.TestRepository;
import com.example.quadalearn.repository.testing.UserTestRepository;
import com.example.quadalearn.service.impl.GeminiService;
import com.example.quadalearn.service.testing.IUserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTestServiceImpl implements IUserTestService {

    private final UserTestRepository userTestRepository;
    private final QuestionRepository questionRepository;
    private final IUserRepository userRepository;
    @Override
    public List<TestHistoryDTO> getTestHistory(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserTest> userTests = userTestRepository.findByUserOrderByTimeDesc(user);

        return userTests.stream().map(ut -> {
            int correctAnswers = (int) ut.getUserAnswers().stream()
                    .filter(UserAnswer::getIsCorrect)
                    .count();
            int totalQuestions = ut.getUserAnswers().size();

            return new TestHistoryDTO(
                    ut.getId(),
                    ut.getTest().getId(),
                    ut.getTest().getName(),
                    ut.getScore(),
                    ut.getTime(),
                    correctAnswers,
                    totalQuestions,
                    ut.getTimeSpent()
            );
        }).collect(Collectors.toList());
    }

    /**
     * Lấy chi tiết 1 bài test
     */
    @Override
    public TestHistoryDetailDTO getTestDetail(Long userTestId) {
        UserTest userTest = userTestRepository.findByIdWithAnswers(userTestId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        // Convert questions - ✅ Chuyển a,b,c,d thành List<String>
        List<QuestionDetailDTO> questions = questionRepository
                .findByTest_Id(userTest.getTest().getId())
                .stream()
                .map(q -> new QuestionDetailDTO(
                        q.getId(),
                        q.getContent(),
                        getOptionsAsList(q), // ✅ Helper method để convert
                        q.getAnswerKey()
                ))
                .collect(Collectors.toList());

        // Convert user answers
        List<UserAnswerDetailDTO> userAnswers = userTest.getUserAnswers().stream()
                .map(ua -> new UserAnswerDetailDTO(
                        ua.getQuestion().getId(),
                        ua.getAnswer(),
                        ua.getIsCorrect()
                ))
                .collect(Collectors.toList());

        return new TestHistoryDetailDTO(
                userTest.getId(),
                userTest.getTest().getId(),
                userTest.getTest().getName(),
                userTest.getScore(),
                userTest.getTime(),
                userTest.getTimeSpent(),
                questions,
                userAnswers
        );
    }

    /**
     * ✅ Helper method: Chuyển a, b, c, d thành List<String>
     */
    private List<String> getOptionsAsList(Question question) {
        List<String> options = new ArrayList<>();
        if (question.getA() != null && !question.getA().trim().isEmpty()) {
            options.add(question.getA());
        }
        if (question.getB() != null && !question.getB().trim().isEmpty()) {
            options.add(question.getB());
        }
        if (question.getC() != null && !question.getC().trim().isEmpty()) {
            options.add(question.getC());
        }
        if (question.getD() != null && !question.getD().trim().isEmpty()) {
            options.add(question.getD());
        }
        return options;
    }

    /**
     * ✅ Kiểm tra đáp án đúng hay sai - Updated để dùng a,b,c,d
     */
}
