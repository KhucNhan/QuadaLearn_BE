package com.example.quadalearn.model.read;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reading_passage")
public class ReadingPassage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    // Quan hệ 1-nhiều: Một bài đọc có nhiều từ vựng
    @OneToMany(mappedBy = "passage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReadingVocabulary> vocabularies = new ArrayList<>();

    // ======= Constructors =======
    public ReadingPassage() {}

    public ReadingPassage(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // ======= Getters & Setters =======
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public List<ReadingVocabulary> getVocabularies() { return vocabularies; }

    public void setVocabularies(List<ReadingVocabulary> vocabularies) { this.vocabularies = vocabularies; }
}
