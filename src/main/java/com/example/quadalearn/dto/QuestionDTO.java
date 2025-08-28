package com.example.quadalearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private Long testId;
    private String content;
    private String type;
    private String answerKey;
    private String knowledgeTag;
}
