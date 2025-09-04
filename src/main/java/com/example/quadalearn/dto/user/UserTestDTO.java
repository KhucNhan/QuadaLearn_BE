package com.example.quadalearn.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTestDTO {
    private Long id;
    private Long userId;
    private Long testId;
    private Double score;
    private String feedback;
}
