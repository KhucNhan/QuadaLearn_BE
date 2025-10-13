package com.example.quadalearn.model.grammar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "grammar_topic")
public class GrammarTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String image;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TopicType> topicTypes;

    // Constructors
    public GrammarTopic() {}

    public GrammarTopic(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    // Getters & Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public List<TopicType> getTopicTypes() {
        return topicTypes;
    }


    public void setTopicTypes(List<TopicType> topicTypes) {
        this.topicTypes = topicTypes;
    }
}
