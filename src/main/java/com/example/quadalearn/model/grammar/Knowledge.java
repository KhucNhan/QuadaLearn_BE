package com.example.quadalearn.model.grammar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "knowledge")
public class Knowledge{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mỗi GrammarDetail thuộc 1 TopicType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    @JsonIgnore
    private TopicType topicType;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
//    cấu trúc
    private String structure;

    @Column(columnDefinition = "TEXT")
//    cách dùng
    private String howToUse;

    @OneToMany(mappedBy = "detail", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<GrammarExample> examples;


    public Knowledge(Long id, TopicType topicType, String title, String description, String structure, String use, List<GrammarExample> examples) {
        this.id = id;
        this.topicType = topicType;
        this.title = title;
        this.description = description;
        this.structure = structure;
        this.howToUse = use;
        this.examples = examples;
    }



    public Knowledge() {

    }

    // Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public TopicType getTopicType() { return topicType; }

    public void setTopicType(TopicType topicType) { this.topicType = topicType; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getStructure() { return structure; }

    public void setStructure(String structure) { this.structure = structure; }

    public String getUsage() { return howToUse; }

    public void setUsage(String usage) { this.howToUse = usage; }

    public List<GrammarExample> getExamples() { return examples; }

    public void setExamples(List<GrammarExample> examples) { this.examples = examples; }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }
}
