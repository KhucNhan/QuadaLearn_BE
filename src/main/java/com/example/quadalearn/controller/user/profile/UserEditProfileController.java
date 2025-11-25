package com.example.quadalearn.controller.user.profile;

import com.example.quadalearn.config.service.FileStorageService;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.service.impl.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserEditProfileController {

    private final FileStorageService fileStorageService;
    private final UserService userService;

    // Lấy user theo id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    // Update thông tin cơ bản (không bao gồm ảnh)
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    // Upload avatar
    @PostMapping("/{id}/upload-avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        String fileName = fileStorageService.saveFile(file);
        String fileUrl = "/uploads/" + fileName;

        User user = userService.updateImage(id, fileUrl);

        Map<String, String> res = new HashMap<>();
        res.put("url", user.getImage());
        return ResponseEntity.ok(res);
    }
}
