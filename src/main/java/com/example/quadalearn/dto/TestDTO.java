package com.example.quadalearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String createdBy; // email hoặc name của user
}
