package com.example.quadalearn.controller;

import com.example.quadalearn.dto.TestSubmissionRequest;
import com.example.quadalearn.model.Test;
import com.example.quadalearn.model.User;
import com.example.quadalearn.service.TestService;
import com.example.quadalearn.service.impl.TestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tests")
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TestController {

    private final TestServiceImpl testService;

    // Lấy tất cả bài kiểm tra
    @GetMapping("")
    public ResponseEntity<List<Test>> getAllTests() {
        return ResponseEntity.ok(testService.findAll());
    }

    // Lấy bài kiểm tra theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTestById(@PathVariable Long id) {
        return testService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo bài kiểm tra
    @PostMapping("")
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        return ResponseEntity.ok(testService.create(test));
    }

    // Cập nhật bài kiểm tra
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTest(@PathVariable Long id, @RequestBody Test test) {
        if (!testService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        test.setId(id);
        return ResponseEntity.ok(testService.update(test));
    }

    // Xóa bài kiểm tra
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Long id) {
        if (!testService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body("Không tìm thấy bài kiểm tra với ID: " + id);
        }
        testService.delete(id);
        return ResponseEntity.ok("Xóa thành công bài kiểm tra với ID: " + id);
    }

    // Lấy bài kiểm tra theo type
    @GetMapping("/type")
    public ResponseEntity<List<Test>> getTestsByType(@RequestParam String type) {
        return ResponseEntity.ok(testService.findByType(type));
    }

    // Lấy bài kiểm tra theo người tạo (creator)
    @PostMapping("/creator")
    public ResponseEntity<List<Test>> getTestsByCreator(@RequestBody User creator) {
        return ResponseEntity.ok(testService.findByCreator(creator));
    }

    @PostMapping("/{testId}/submit")
    public ResponseEntity<?> submitTest(
            @PathVariable Long testId,
            @RequestBody TestSubmissionRequest request) {
        try {
            // user = null vì không đăng nhập
            String analysis = testService.submitTest(null, testId, request);
            return ResponseEntity.ok(Map.of(
                    "testId", testId,
                    "scoreAnalysis", analysis
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
