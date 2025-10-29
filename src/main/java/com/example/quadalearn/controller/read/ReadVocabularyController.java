package com.example.quadalearn.controller.read;

import com.example.quadalearn.model.read.ReadingVocabulary;
import com.example.quadalearn.service.impl.read.ReadVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vocabularies")
@CrossOrigin(origins = "*")
public class ReadVocabularyController {

    @Autowired
    private ReadVocabularyService vocabularyService;

    // Lấy tất cả từ vựng
    @GetMapping
    public ResponseEntity<List<ReadingVocabulary>> getAll() {
        return ResponseEntity.ok(vocabularyService.findAll());
    }

    // Lấy danh sách từ vựng theo bài đọc
    @GetMapping("/passage/{passageId}")
    public ResponseEntity<List<ReadingVocabulary>> getByPassage(@PathVariable Long passageId) {
        return ResponseEntity.ok(vocabularyService.findByPassageId(passageId));
    }

    // Lấy từ vựng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ReadingVocabulary> getById(@PathVariable Long id) {
        Optional<ReadingVocabulary> vocab = vocabularyService.findById(id);
        return vocab.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm mới từ vựng
    @PostMapping
    public ResponseEntity<ReadingVocabulary> create(@RequestBody ReadingVocabulary vocab) {
        return ResponseEntity.ok(vocabularyService.save(vocab));
    }

    // Cập nhật từ vựng
    @PutMapping("/{id}")
    public ResponseEntity<ReadingVocabulary> update(@PathVariable Long id, @RequestBody ReadingVocabulary updatedVocab) {
        Optional<ReadingVocabulary> existing = vocabularyService.findById(id);
        if (existing.isPresent()) {
            ReadingVocabulary vocab = existing.get();
            vocab.setWord(updatedVocab.getWord());
            vocab.setMeaning(updatedVocab.getMeaning());
            vocab.setPassage(updatedVocab.getPassage());
            return ResponseEntity.ok(vocabularyService.save(vocab));
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa từ vựng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vocabularyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}