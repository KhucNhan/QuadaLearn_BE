package com.example.quadalearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyDTO {
    private Long id;
    private String word;
    private String meaning;
    private String exampleSentence;
    private String level;
}
