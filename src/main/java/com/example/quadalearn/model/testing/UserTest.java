package com.example.quadalearn.model.testing;

import com.example.quadalearn.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    private Double score; // điểm số

    @Column(name = "time")
    private LocalDateTime time; // thời điểm hoàn thành bài test

    @Column(name = "time_spent")
    private Integer timeSpent; // thời gian làm bài (giây)

    @OneToMany(mappedBy = "userTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAnswer> userAnswers;

    // Constructor để tạo mới
    public UserTest(User user, Test test, Double score, LocalDateTime time, Integer timeSpent) {
        this.user = user;
        this.test = test;
        this.score = score;
        this.time = time;
        this.timeSpent = timeSpent;
    }
}
