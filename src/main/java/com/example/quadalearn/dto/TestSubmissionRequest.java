package com.example.quadalearn.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestSubmissionRequest {
    private String aim;
    private List<UserAnswerDTO> answers;
}
