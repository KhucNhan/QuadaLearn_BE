package com.example.quadalearn.controller.course.question;

import com.example.quadalearn.dto.course.question.QuestionDTO;
import com.example.quadalearn.model.testing.Question;
import com.example.quadalearn.service.testing.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class QuestionController {

    private final IQuestionService questionService;

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
    public ResponseEntity<List<QuestionDTO>> getQuestionsByTestId(@PathVariable Long testId) {
        List<Question> questions = questionService.findByTestId(testId);

        List<QuestionDTO> dtoList = questions.stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setId(q.getId());
            dto.setTestId(q.getTest().getId());
            dto.setContent(q.getContent());
            dto.setType(q.getType());
            dto.setAnswerKey(q.getAnswerKey());
            dto.setKnowledgeTag(q.getKnowledgeTag());

            // ghép a,b,c,d thành list
            List<String> options = new ArrayList<>();
            if (q.getA() != null) options.add(q.getA());
            if (q.getB() != null) options.add(q.getB());
            if (q.getC() != null) options.add(q.getC());
            if (q.getD() != null) options.add(q.getD());

            dto.setOptions(options);

            return dto;
        }).toList();

        return ResponseEntity.ok(dtoList);
    }


    // GET questions by knowledge tag
    @GetMapping("/tag/{knowledgeTag}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByTag(@PathVariable String knowledgeTag) {
        try {
            List<QuestionDTO> questions = questionService.findByKnowledgeTag(knowledgeTag);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // GET questions by type
    @GetMapping("/type")
    public ResponseEntity<List<Question>> getQuestionsByType(@RequestParam String type) {
        return ResponseEntity.ok(questionService.findByType(type));
    }
}