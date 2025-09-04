package com.example.quadalearn.dto.course.vocbulary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVocabularyDTO {
    private Long id;
    private Long userId;
    private Long vocabularyId;
    private Boolean mastered;
}
