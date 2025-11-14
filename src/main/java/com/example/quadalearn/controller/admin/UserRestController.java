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
    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return new ResponseEntity<>(userService.toDTO(updatedUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
