package com.example.quadalearn.dto.user.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestHistoryDTO {
    private Long id;
    private Long testId;
    private String testName;
    private Double score;
    private LocalDateTime time;
    private Integer correctAnswers;
    private Integer totalQuestions;
    private Integer timeSpent; // seconds
}
