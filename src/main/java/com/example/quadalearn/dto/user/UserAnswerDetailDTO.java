package com.example.quadalearn.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerDetailDTO { // ✅ Đổi tên từ UserAnswerDTO
    private Long questionId;
    private String answer;
    private Boolean isCorrect;
}
