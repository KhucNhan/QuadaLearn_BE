package com.example.quadalearn.controller.read;

import com.example.quadalearn.model.read.ReadingPassage;
import com.example.quadalearn.service.read.IReadPassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reading-passages")
@CrossOrigin(origins = "*") // Cho phép frontend gọi API
public class ReadPassageController {

    @Autowired
    private IReadPassageService passageService;

    // Lấy danh sách tất cả bài đọc
    @GetMapping
    public ResponseEntity<List<ReadingPassage>> getAllPassages() {
        return ResponseEntity.ok(passageService.findAll());
    }

    // Lấy bài đọc theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ReadingPassage> getPassageById(@PathVariable Long id) {
        Optional<ReadingPassage> passage = passageService.findById(id);
        return passage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm bài đọc mới
    @PostMapping
    public ResponseEntity<ReadingPassage> createPassage(@RequestBody ReadingPassage passage) {
        ReadingPassage savedPassage = passageService.save(passage);
        return ResponseEntity.ok(savedPassage);
    }

    // Cập nhật bài đọc
    @PutMapping("/{id}")
    public ResponseEntity<ReadingPassage> updatePassage(@PathVariable Long id, @RequestBody ReadingPassage updatedPassage) {
        Optional<ReadingPassage> existing = passageService.findById(id);
        if (existing.isPresent()) {
            ReadingPassage passage = existing.get();
            passage.setTitle(updatedPassage.getTitle());
            passage.setContent(updatedPassage.getContent());
            passage.setVocabularies(updatedPassage.getVocabularies());
            return ResponseEntity.ok(passageService.save(passage));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa bài đọc
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassage(@PathVariable Long id) {
        passageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}