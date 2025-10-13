package com.example.quadalearn.model.grammar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "topic_type")
public class TopicType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    @JsonIgnore
    private GrammarTopic topic;

    @Column(nullable = false)
    private String title;

    private String level;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "topicType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Knowledge> grammarDetails;

    // Constructors
    public TopicType() {}

    public TopicType(Long id, GrammarTopic topic, String title, String level, String image, String description, List<Knowledge> grammarDetails) {
        this.id = id;
        this.topic = topic;
        this.title = title;
        this.level = level;
        this.image = image;
        this.description = description;
        this.grammarDetails = grammarDetails;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public GrammarTopic getTopic() { return topic; }

    public void setTopic(GrammarTopic topic) { this.topic = topic; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<Knowledge> getGrammarDetails() { return grammarDetails; }

    public void setGrammarDetails(List<Knowledge> grammarDetails) { this.grammarDetails = grammarDetails; }
}
