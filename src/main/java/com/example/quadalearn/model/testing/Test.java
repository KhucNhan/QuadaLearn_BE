package com.example.quadalearn.model.testing;

import com.example.quadalearn.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String type;

    // Người tạo test
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
