package com.example.quadalearn.dto.user.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailDTO {
    private Long id;
    private String content;
    private List<String> options;
    private String answerKey;
}
