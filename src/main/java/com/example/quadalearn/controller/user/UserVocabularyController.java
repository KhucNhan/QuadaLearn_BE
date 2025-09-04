package com.example.quadalearn.controller.user;

import com.example.quadalearn.model.learning.UserVocabulary;
import com.example.quadalearn.service.learning.IUserVocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-vocabularies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserVocabularyController {

    private final IUserVocabularyService userVocabularyService;

    // GET all
    @GetMapping("")
    public ResponseEntity<List<UserVocabulary>> getAllUserVocabularies() {
        return ResponseEntity.ok(userVocabularyService.findAll());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserVocabularyById(@PathVariable Long id) {
        Optional<UserVocabulary> userVocabulary = userVocabularyService.findById(id);
        if (userVocabulary.isPresent()) {
            return ResponseEntity.ok(userVocabulary.get());
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy UserVocabulary với ID: " + id);
        }
    }

    // GET by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserVocabulary>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userVocabularyService.findByUserId(userId));
    }

    // CREATE
    @PostMapping("")
    public ResponseEntity<UserVocabulary> createUserVocabulary(@RequestBody UserVocabulary userVocabulary) {
        return ResponseEntity.ok(userVocabularyService.create(userVocabulary));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserVocabulary(@PathVariable Long id, @RequestBody UserVocabulary userVocabulary) {
        Optional<UserVocabulary> existing = userVocabularyService.findById(id);
        if (existing.isPresent()) {
            userVocabulary.setId(id);
            return ResponseEntity.ok(userVocabularyService.update(userVocabulary));
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy UserVocabulary với ID: " + id);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserVocabulary(@PathVariable Long id) {
        Optional<UserVocabulary> existing = userVocabularyService.findById(id);
        if (existing.isPresent()) {
            userVocabularyService.delete(id);
            return ResponseEntity.ok("Xóa thành công UserVocabulary với ID: " + id);
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy UserVocabulary với ID: " + id);
        }
    }

    // CHECK existence of user-vocabulary pair
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsUserVocabulary(@RequestParam Long userId,
                                                        @RequestParam Long vocabularyId) {
        boolean exists = userVocabularyService.exists(userId, vocabularyId);
        return ResponseEntity.ok(exists);
    }
}