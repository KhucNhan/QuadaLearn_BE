package com.example.quadalearn.dto;

import com.example.quadalearn.dto.user.UserAnswerDTO;
import lombok.Data;

import java.util.List;

@Data
public class TestSubmissionRequest {
    private String aim;
    private List<UserAnswerDTO> answers;
    private int timeSpent;
}