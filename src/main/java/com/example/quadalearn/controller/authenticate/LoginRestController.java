package com.example.quadalearn.controller.authenticate;

import com.example.quadalearn.config.service.JwtResponse;
import com.example.quadalearn.config.service.JwtService;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.service.auth.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginRestController {

    private static final Logger log = LoggerFactory.getLogger(LoginRestController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User userInfo = userService.findByEmail(user.getEmail());
            if (userInfo == null) {
                log.warn("❌ User not found: {}", user.getEmail());
                throw new UsernameNotFoundException("User not found");
            }
            if (!passwordEncoder.matches(user.getPassword(), userInfo.getPassword())) {
                log.warn("❌ Bad credentials for email={}", user.getEmail());
                throw new BadCredentialsException("Bad credentials");
            }

            // Authenticate
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Tạo JWT
            String jwt = jwtService.generateTokenLogin(auth);
            boolean needsCompletion = (userInfo.getGoal() == null || userInfo.getCurrentLevel() == null);


            return ResponseEntity.ok(new JwtResponse(
                    userInfo.getId(),
                    jwt,
                    userInfo.getName(),
                    auth.getAuthorities(),
                    needsCompletion
            ));

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            log.error("❌ Login FAILED: email={}", user.getEmail(), e);
            throw e;
        }
    }

    @PutMapping("/complete-profile")
    public ResponseEntity<?> completeProfile(@RequestBody Map<String, String> payload, Authentication authentication) {
        String email = authentication.getName();

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Lấy dữ liệu từ body
        String currentLevel = payload.get("currentLevel");
        String goal = payload.get("goal");

        // Update user
        user.setCurrentLevel(currentLevel);
        user.setGoal(goal);

        userService.save(user);

        // Trả lại user đã update (có thể kèm JWT hoặc không)
        return ResponseEntity.ok(user);
    }
}
