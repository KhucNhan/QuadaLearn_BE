package com.example.quadalearn.model.testing;

import com.example.quadalearn.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_tests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User làm bài test
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private Double score; // điểm số / % chính xác
}
