package com.example.quadalearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProgressDTO {
    private Long id;
    private Long userId;
    private Long courseId;
    private Double progress; // %
}
