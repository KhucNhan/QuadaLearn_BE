package com.example.quadalearn.model.read;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "reading_vocabulary")
public class ReadingVocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String meaning;

    // Quan hệ nhiều-1: nhiều từ vựng thuộc về một bài đọc
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_passage")
    @JsonBackReference
    private ReadingPassage passage;

    // ======= Constructors =======
    public ReadingVocabulary() {}

    public ReadingVocabulary(String word, String meaning, ReadingPassage passage) {
        this.word = word;
        this.meaning = meaning;
        this.passage = passage;
    }

    // ======= Getters & Setters =======
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getWord() { return word; }

    public void setWord(String word) { this.word = word; }

    public String getMeaning() { return meaning; }

    public void setMeaning(String meaning) { this.meaning = meaning; }

    public ReadingPassage getPassage() { return passage; }

    public void setPassage(ReadingPassage passage) { this.passage = passage; }
}
