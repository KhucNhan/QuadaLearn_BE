package com.example.quadalearn.controller.authenticate;

import com.example.quadalearn.config.service.JwtService;
import com.example.quadalearn.model.auth.Role;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.service.auth.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import com.example.quadalearn.config.service.JwtResponse;

@RestController
@RequestMapping("/api/auth")
public class RegisterRestController {
    private static final Logger log = LoggerFactory.getLogger(LoginRestController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        log.info("👉 Register attempt: email={}", user.getEmail());

        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        // Gán role mặc định
        user.setRoles(Set.of(new Role(2L, "ROLE_USER")));

        // Lưu vào DB
        User savedUser = userService.add(user);

        log.info("✅ Register SUCCESS: email={}", savedUser.getEmail());

        // Chỉ trả về user info, không login luôn
        return ResponseEntity.ok(savedUser);
    }

}
