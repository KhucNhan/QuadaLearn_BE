package com.example.quadalearn.dto.user.test;

import com.example.quadalearn.dto.user.UserAnswerDTO;
import com.example.quadalearn.dto.user.UserAnswerDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestHistoryDetailDTO {
    private Long id;
    private Long testId;
    private String testName;
    private Double score;
    private LocalDateTime time;
    private Integer timeSpent;
    private List<QuestionDetailDTO> questions;
    private List<UserAnswerDetailDTO> userAnswers;
}
