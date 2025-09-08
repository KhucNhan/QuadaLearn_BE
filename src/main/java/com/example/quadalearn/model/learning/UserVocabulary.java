package com.example.quadalearn.model.learning;

import com.example.quadalearn.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_vocabularies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vocabulary_id")
    private Vocabulary vocabulary;

    private Boolean isMastered; // đã nhớ từ chưa
}
