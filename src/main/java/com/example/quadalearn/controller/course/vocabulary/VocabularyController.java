package com.example.quadalearn.controller.course.vocabulary;

import com.example.quadalearn.model.learning.Vocabulary;
import com.example.quadalearn.service.learning.IVocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vocabularies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VocabularyController {

    private final IVocabularyService vocabularyService;

    // GET all
    @GetMapping("")
    public ResponseEntity<List<Vocabulary>> getAllVocabularies() {
        return ResponseEntity.ok(vocabularyService.findAll());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getVocabularyById(@PathVariable Long id) {
        Optional<Vocabulary> vocabulary = vocabularyService.findById(id);
        if (vocabulary.isPresent()) {
            return ResponseEntity.ok(vocabulary.get());
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy Vocabulary với ID: " + id);
        }
    }

    // GET by level
    @GetMapping("/level/{level}")
    public ResponseEntity<List<Vocabulary>> getByLevel(@PathVariable String level) {
        List<Vocabulary> vocabularies = vocabularyService.findByLevel(level);
        return ResponseEntity.ok(vocabularies);
    }

    // SEARCH by word
    @GetMapping("/search")
    public ResponseEntity<List<Vocabulary>> searchByWord(@RequestParam String keyword) {
        List<Vocabulary> vocabularies = vocabularyService.searchByWord(keyword);
        return ResponseEntity.ok(vocabularies);
    }

    // CREATE
    @PostMapping("")
    public ResponseEntity<Vocabulary> createVocabulary(@RequestBody Vocabulary vocabulary) {
        return ResponseEntity.ok(vocabularyService.create(vocabulary));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVocabulary(@PathVariable Long id, @RequestBody Vocabulary vocabulary) {
        Optional<Vocabulary> existing = vocabularyService.findById(id);
        if (existing.isPresent()) {
            vocabulary.setId(id);
            return ResponseEntity.ok(vocabularyService.update(vocabulary));
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy Vocabulary với ID: " + id);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVocabulary(@PathVariable Long id) {
        Optional<Vocabulary> existing = vocabularyService.findById(id);
        if (existing.isPresent()) {
            vocabularyService.delete(id);
            return ResponseEntity.ok("Xóa thành công Vocabulary với ID: " + id);
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy Vocabulary với ID: " + id);
        }
    }
}