package com.example.quadalearn.controller;

import com.example.quadalearn.model.UserTest;
import com.example.quadalearn.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-tests")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserTestController {

    private final UserTestService userTestService;

    // GET all user tests
    @GetMapping("")
    public ResponseEntity<List<UserTest>> getAllUserTests() {
        return ResponseEntity.ok(userTestService.findAll());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserTestById(@PathVariable Long id) {
        Optional<UserTest> userTest = userTestService.findById(id);
        if (userTest.isPresent()) {
            return ResponseEntity.ok(userTest.get());
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy UserTest với ID: " + id);
        }
    }

    // CREATE
    @PostMapping("")
    public ResponseEntity<UserTest> createUserTest(@RequestBody UserTest userTest) {
        return ResponseEntity.ok(userTestService.create(userTest));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserTest(@PathVariable Long id, @RequestBody UserTest userTest) {
        Optional<UserTest> existing = userTestService.findById(id);
        if (existing.isPresent()) {
            userTest.setId(id);
            return ResponseEntity.ok(userTestService.update(userTest));
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy UserTest với ID: " + id);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserTest(@PathVariable Long id) {
        Optional<UserTest> existing = userTestService.findById(id);
        if (existing.isPresent()) {
            userTestService.delete(id);
            return ResponseEntity.ok("Xóa thành công UserTest với ID: " + id);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy UserTest với ID: " + id);
        }
    }

    // GET by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTest>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userTestService.findByUserId(userId));
    }

    // GET by testId
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<UserTest>> getByTestId(@PathVariable Long testId) {
        return ResponseEntity.ok(userTestService.findByTestId(testId));
    }

    // GET latest UserTest of a user
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<?> getLatestByUserId(@PathVariable Long userId) {
        Optional<UserTest> latest = userTestService.findLatestByUserId(userId);
        if (latest.isPresent()) {
            return ResponseEntity.ok(latest.get());
        } else {
            return ResponseEntity.status(404)
                    .body("Không tìm thấy UserTest mới nhất của userId: " + userId);
        }
    }
}
