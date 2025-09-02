package com.example.quadalearn.controller;

import com.example.quadalearn.model.UserProgress;
import com.example.quadalearn.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-progress")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserProgressController {

    private final UserProgressService userProgressService;

    // GET all user progress
    @GetMapping("")
    public ResponseEntity<List<UserProgress>> getAllUserProgress() {
        return ResponseEntity.ok(userProgressService.findAll());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProgressById(@PathVariable Long id) {
        return userProgressService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create
    @PostMapping("")
    public ResponseEntity<UserProgress> createUserProgress(@RequestBody UserProgress userProgress) {
        return ResponseEntity.ok(userProgressService.create(userProgress));
    }

    // PUT update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProgress(@PathVariable Long id, @RequestBody UserProgress userProgress) {
        if (!userProgressService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userProgress.setId(id);
        return ResponseEntity.ok(userProgressService.update(userProgress));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserProgress(@PathVariable Long id) {
        if (!userProgressService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body("Không tìm thấy UserProgress với ID: " + id);
        }
        userProgressService.delete(id);
        return ResponseEntity.ok("Xóa thành công UserProgress với ID: " + id);
    }

    // GET progress by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserProgress>> getProgressByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.findByUserId(userId));
    }

    // GET progress by courseId
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<UserProgress>> getProgressByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(userProgressService.findByCourseId(courseId));
    }

    // GET progress by userId and courseId
    @GetMapping("/user-course")
    public ResponseEntity<?> getByUserIdAndCourseId(@RequestParam Long userId,
                                                    @RequestParam Long courseId) {
        Optional<UserProgress> progress = userProgressService.findByUserIdAndCourseId(userId, courseId);
        if (progress.isPresent()) {
            return ResponseEntity.ok(progress.get());
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy tiến trình học với userId = " + userId + " và courseId = " + courseId);
        }
    }

}
