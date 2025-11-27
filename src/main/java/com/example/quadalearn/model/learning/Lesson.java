package com.example.quadalearn.model.learning;

import com.example.quadalearn.model.grammar.Knowledge;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    private String title;
    @Column(length = 2000)
    private String content;
    private String knowledgeTag;

    // ✅ Một Lesson có thể có nhiều Knowledge
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Knowledge> knowledges;
}
