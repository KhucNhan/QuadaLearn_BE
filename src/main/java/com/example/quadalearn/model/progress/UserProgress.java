package com.example.quadalearn.model.progress;

import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.learning.Course;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Double progress; // % tiến độ
}
