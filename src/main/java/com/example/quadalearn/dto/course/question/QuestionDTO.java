package com.example.quadalearn.dto.course.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> options;
}
