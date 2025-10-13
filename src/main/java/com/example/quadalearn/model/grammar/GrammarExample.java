package com.example.quadalearn.model.grammar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "grammar_example")
public class GrammarExample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ví dụ thuộc về 1 GrammarPoint
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_id", nullable = false)
    @JsonIgnore
    private Knowledge detail;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String sentenceEn; // câu ví dụ tiếng Anh

    @Column(columnDefinition = "TEXT")
    private String sentenceVi; // dịch tiếng Việt

    @Column(columnDefinition = "TEXT")
    private String note; // ghi chú thêm nếu có

    // Constructors
    public GrammarExample() {}

    public GrammarExample(Long id, Knowledge detail, String sentenceEn, String sentenceVi, String note) {
        this.id = id;
        this.detail = detail;
        this.sentenceEn = sentenceEn;
        this.sentenceVi = sentenceVi;
        this.note = note;
    }

    // Getters & Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getSentenceEn() { return sentenceEn; }

    public void setSentenceEn(String sentenceEn) { this.sentenceEn = sentenceEn; }

    public String getSentenceVi() { return sentenceVi; }

    public void setSentenceVi(String sentenceVi) { this.sentenceVi = sentenceVi; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public Knowledge getDetail() {
        return detail;
    }

    public void setDetail(Knowledge detail) {
        this.detail = detail;
    }
}
