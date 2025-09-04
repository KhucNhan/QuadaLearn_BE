package com.example.quadalearn.controller.user;

import com.example.quadalearn.model.testing.UserAnswer;
import com.example.quadalearn.service.testing.IUserAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-answers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserAnswerController {

    private final IUserAnswerService userAnswerService;

    // Lấy tất cả user answer
    @GetMapping("")
    public ResponseEntity<List<UserAnswer>> getAllUserAnswers() {
        return ResponseEntity.ok(userAnswerService.findAll());
    }

    // Lấy user answer theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAnswerById(@PathVariable Long id) {
        return userAnswerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm user answer
    @PostMapping("")
    public ResponseEntity<UserAnswer> createUserAnswer(@RequestBody UserAnswer userAnswer) {
        return ResponseEntity.ok(userAnswerService.create(userAnswer));
    }

    // Cập nhật user answer
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserAnswer(@PathVariable Long id, @RequestBody UserAnswer userAnswer) {
        if (!userAnswerService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userAnswer.setId(id);
        return ResponseEntity.ok(userAnswerService.update(userAnswer));
    }

    // Xóa user answer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAnswer(@PathVariable Long id) {
        if (!userAnswerService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body("Không tìm thấy userAnswer với ID: " + id);
        }
        userAnswerService.delete(id);
        return ResponseEntity.ok("Xóa thành công userAnswer với ID: " + id);
    }

    // Lấy user answers theo userTestId
    @GetMapping("/user-test/{userTestId}")
    public ResponseEntity<List<UserAnswer>> getByUserTestId(@PathVariable Long userTestId) {
        return ResponseEntity.ok(userAnswerService.findByUserTestId(userTestId));
    }

    // Lấy user answers theo questionId
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<UserAnswer>> getByQuestionId(@PathVariable Long questionId) {
        return ResponseEntity.ok(userAnswerService.findByQuestionId(questionId));
    }
}
