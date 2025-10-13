package com.example.quadalearn.controller.grammar;

import com.example.quadalearn.model.grammar.GrammarTopic;
import com.example.quadalearn.service.grammar.IGrammarTopicGennerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grammar-topics")
public class GrammarTopicController {

    private final IGrammarTopicGennerateService topicService;

    @Autowired
    public GrammarTopicController(IGrammarTopicGennerateService topicService) {
        this.topicService = topicService;
    }

    // Lấy tất cả grammar topics
    @GetMapping
    public ResponseEntity<List<GrammarTopic>> getAllTopics() {
        return ResponseEntity.ok(topicService.findAll());
    }

    // Lấy 1 topic theo ID
    @GetMapping("/{id}")
    public ResponseEntity<GrammarTopic> getTopicById(@PathVariable Long id) {
        GrammarTopic topic = topicService.findById(id);
        if (topic == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(topic);
    }

    // Thêm mới một topic
    @PostMapping
    public ResponseEntity<GrammarTopic> createTopic(@RequestBody GrammarTopic topic) {
        GrammarTopic created = topicService.add(topic);
        return ResponseEntity.ok(created);
    }

    // Xóa một topic theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
