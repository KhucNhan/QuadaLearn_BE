package com.example.quadalearn.model.testing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_test_id", nullable = false)
    @JsonIgnore // Tránh circular reference khi serialize JSON
    private UserTest userTest;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "answer")
    private String answer; // câu trả lời của user

    @Column(name = "is_correct")
    private Boolean isCorrect; // đúng hay sai

    // Constructor để tạo mới
    public UserAnswer(UserTest userTest, Question question, String answer, Boolean isCorrect) {
        this.userTest = userTest;
        this.question = question;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
}
