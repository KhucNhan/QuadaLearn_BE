package com.example.quadalearn.controller.grammar;

import com.example.quadalearn.model.grammar.TopicType;
import com.example.quadalearn.service.impl.grammar.TopicTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic-types")
@AllArgsConstructor
public class TopicTypeController {

    private final TopicTypeService topicTypeService;


    @GetMapping("/by-topic/{topicId}")
    public ResponseEntity<List<TopicType>> getTypesByTopicId(@PathVariable Long topicId) {
        return ResponseEntity.ok(topicTypeService.getTypesByTopicId(topicId));
    }



    // Lấy tất cả các topic type
    @GetMapping
    public ResponseEntity<List<TopicType>> getAllTopicTypes() {
        return ResponseEntity.ok(topicTypeService.findAll());
    }

    // Lấy topic type theo ID
    @GetMapping("/{id}")
    public ResponseEntity<TopicType> getTopicTypeById(@PathVariable Long id) {
        TopicType type = topicTypeService.findById(id);
        if (type == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(type);
    }

    // Thêm mới topic type
    @PostMapping
    public ResponseEntity<TopicType> createTopicType(@RequestBody TopicType topicType) {
        TopicType created = topicTypeService.add(topicType);
        return ResponseEntity.ok(created);
    }

    // Xoá topic type theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopicType(@PathVariable Long id) {
        topicTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
