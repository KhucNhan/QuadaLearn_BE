package com.example.quadalearn.controller.grammar;

import com.example.quadalearn.model.grammar.Knowledge;
import com.example.quadalearn.service.grammar.IKnowledgeGennerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    private final IKnowledgeGennerateService knowledgeService;

    @GetMapping("/by-lesson/{lessonId}")
    public ResponseEntity<List<Knowledge>> getKnowledgeByLessonId(@PathVariable Long lessonId) {
        List<Knowledge> result = knowledgeService.getKnowledgeByLessonId(lessonId);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-type/{typeId}")
    public ResponseEntity<List<Knowledge>> getKnowledgeByTypeId(@PathVariable Long typeId) {
        List<Knowledge> result = knowledgeService.getKnowledgeByTypeId(typeId);
        return ResponseEntity.ok(result);
    }

    @Autowired
    public KnowledgeController(IKnowledgeGennerateService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }
    // Lấy tất cả knowledge
    @GetMapping
    public ResponseEntity<List<Knowledge>> getAllKnowledge() {
        return ResponseEntity.ok(knowledgeService.findAll());
    }

    // Lấy knowledge theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Knowledge> getKnowledgeById(@PathVariable Long id) {
        Knowledge knowledge = knowledgeService.findById(id);
        if (knowledge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(knowledge);
    }

    // Tạo knowledge mới
    @PostMapping
    public ResponseEntity<Knowledge> createKnowledge(@RequestBody Knowledge knowledge) {
        Knowledge created = knowledgeService.add(knowledge);
        return ResponseEntity.ok(created);
    }

    // Xoá knowledge theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnowledge(@PathVariable Long id) {
        knowledgeService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
