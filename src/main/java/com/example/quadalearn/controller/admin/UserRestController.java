package com.example.quadalearn.controller.admin;

import com.example.quadalearn.config.service.FileStorageService;
import com.example.quadalearn.dto.UserDTO;
import com.example.quadalearn.model.auth.Role;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.service.impl.auth.RoleService;
import com.example.quadalearn.service.impl.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private final FileStorageService fileStorageService;

    public UserRestController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /* ---------------- GET ALL USER ------------------------ */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /* ---------------- GET USER BY ID ------------------------ */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Found User", HttpStatus.NO_CONTENT);
    }

    /* ---------------- CREATE NEW USER ------------------------ */
    @PostMapping(value = "/users", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createUser(
            @RequestPart("user") User user,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            // ✅ Nếu có upload ảnh thì lưu file
            if (file != null && !file.isEmpty()) {
                String fileName = fileStorageService.saveFile(file);
                user.setImage("/uploads/" + fileName);
            }

            // ✅ Gọi service thêm user
            User created = userService.add(user);

            // ✅ Nếu trùng email → service trả null
            if (created == null) {
                return ResponseEntity.badRequest().body("User Existed!");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(created);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    /* ---------------- UPDATE USER ------------------------ */
    @PutMapping(value = "/users/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestPart("user") User user,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            User existingUser = userService.getUser(id);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Cập nhật thông tin text
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setGender(user.getGender());
            existingUser.setGoal(user.getGoal());
            existingUser.setCurrentLevel(user.getCurrentLevel());
            existingUser.setStatus(user.getStatus());

            // Nếu có upload ảnh mới
            if (file != null && !file.isEmpty()) {
                String fileName = fileStorageService.saveFile(file);
                existingUser.setImage("/uploads/" + fileName);
            }

            User updated = userService.save(existingUser);

            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/users/{id}/ban")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        user.setStatus(User.Status.BANNED);
        userService.save(user);
        return ResponseEntity.ok("User banned");
    }

    @PutMapping("/users/{id}/unban")
    public ResponseEntity<?> unbanUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        user.setStatus(User.Status.ACTIVE);
        userService.save(user);
        return ResponseEntity.ok("User unbanned");
    }


    /* ---------------- DELETE USER ------------------------ */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }


}
