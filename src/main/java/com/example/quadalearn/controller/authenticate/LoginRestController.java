package com.example.quadalearn.controller.authenticate;

import com.example.quadalearn.config.service.JwtResponse;
import com.example.quadalearn.config.service.JwtService;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.repository.auth.IUserRepository;
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

import java.time.LocalDateTime;
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
    @Autowired
    private IUserRepository userRepository;

    // package com.example.quadalearn.controller.authenticate;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User userInfo = userService.findByEmail(user.getEmail());
            if (userInfo == null) {
                log.warn("❌ User not found: {}", user.getEmail());
                throw new UsernameNotFoundException("User not found");
            }

            // 1. ✅ KIỂM TRA TRẠNG THÁI (BANNED) NGAY SAU KHI TÌM THẤY USER
            if (userInfo.getStatus() == User.Status.BANNED) {
                log.warn("❌ Login FAILED: Account is banned for email={}", user.getEmail());
                // Trả về lỗi BadCredentials để tránh lộ thông tin account
                throw new BadCredentialsException("Account is banned");
            }

            // 2. KIỂM TRA MẬT KHẨU
            if (!passwordEncoder.matches(user.getPassword(), userInfo.getPassword())) {
                log.warn("❌ Bad credentials for email={}", user.getEmail());
                throw new BadCredentialsException("Bad credentials");
            }

            // 3. XÁC THỰC VÀ TẠO JWT (Chỉ thực hiện khi user là ACTIVE và mật khẩu đúng)
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            User userInf = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.updateLastLogin(userInf.getId(), LocalDateTime.now());

            // Tạo JWT và Response
            String jwt = jwtService.generateTokenLogin(auth);
            boolean needsCompletion = (userInfo.getGoal() == null || userInfo.getCurrentLevel() == null);


            return ResponseEntity.ok(new JwtResponse(
                    userInfo.getId(),
                    jwt,
                    userInfo.getName(),
                    userInfo.getImage(),
                    auth.getAuthorities(),
                    needsCompletion,
                    userInfo.getStatus().name()
            ));

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            log.error("❌ Login FAILED: email={}", user.getEmail(), e);
            // Trả về Exception tiêu chuẩn để frontend hiển thị lỗi
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

        userService.updateUser(user.getId(), user);

        // Trả lại user đã update (có thể kèm JWT hoặc không)
        return ResponseEntity.ok(user);
    }
}
