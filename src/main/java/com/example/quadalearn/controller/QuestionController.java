package com.example.quadalearn.controller;

import com.example.quadalearn.model.Question;
import com.example.quadalearn.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // GET all questions
    @GetMapping("")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.findAll());
    }

    // GET question by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        return questionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create new question
    @PostMapping("")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.create(question));
    }

    // PUT update question
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        if (!questionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        question.setId(id); // ensure correct ID is set
        return ResponseEntity.ok(questionService.update(question));
    }

    // DELETE question
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        if (!questionService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body("Không tìm thấy question với ID: " + id);
        }
        questionService.delete(id);
        return ResponseEntity.ok("Xóa thành công câu hỏi với ID: " + id);
    }

    // GET questions by test ID
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<Question>> getQuestionsByTestId(@PathVariable Long testId) {
        return ResponseEntity.ok(questionService.findByTestId(testId));
    }

    // GET questions by knowledge tag
    @GetMapping("/tag")
    public ResponseEntity<List<Question>> getQuestionsByKnowledgeTag(@RequestParam String tag) {
        return ResponseEntity.ok(questionService.findByKnowledgeTag(tag));
    }

    // GET questions by type
    @GetMapping("/type")
    public ResponseEntity<List<Question>> getQuestionsByType(@RequestParam String type) {
        return ResponseEntity.ok(questionService.findByType(type));
    }
}