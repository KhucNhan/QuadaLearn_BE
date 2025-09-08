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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            log.info("👉 Login attempt: email={}", user.getEmail());

            // Lấy thông tin user từ DB
            User userInfo = userService.findByEmail(user.getEmail());
            if (userInfo == null) {
                log.warn("❌ User not found: {}", user.getEmail());
                throw new UsernameNotFoundException("User not found");
            }

            // Kiểm tra password
            log.info(user.getPassword());
            log.info(userInfo.getPassword());
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
            log.info("✅ Login SUCCESS: email={}", user.getEmail());

            return ResponseEntity.ok(new JwtResponse(
                    userInfo.getId(),
                    jwt,
                    userInfo.getName(),
                    auth.getAuthorities()
            ));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            log.error("❌ Login FAILED: email={}", user.getEmail(), e);
            throw e;
        }
    }
}
