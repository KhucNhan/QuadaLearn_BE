package com.example.quadalearn.controller.user;

import com.example.quadalearn.dto.user.test.TestHistoryDTO;
import com.example.quadalearn.dto.user.test.TestHistoryDetailDTO;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.testing.UserTest;
import com.example.quadalearn.service.testing.IUserTestService;
import com.example.quadalearn.util.SecurityUtils;
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

    private final IUserTestService userTestService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TestHistoryDTO>> getTestHistory(@PathVariable Long id) {
        List<TestHistoryDTO> history = userTestService.getTestHistory(id);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestHistoryDetailDTO> getTestDetail(@PathVariable Long id) {
        try {
            TestHistoryDetailDTO detail = userTestService.getTestDetail(id);
            return ResponseEntity.ok(detail);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
