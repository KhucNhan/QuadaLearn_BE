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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

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
            log.info("👉 Login attempt: email={}, rawPassword={}", user.getEmail(), user.getPassword());

            // Kiểm tra DB trước khi authenticate
            User userInfo = userService.findByEmail(user.getEmail());
            if (userInfo != null) {
                boolean match = passwordEncoder.matches(user.getPassword(), userInfo.getPassword());
                log.info("🔑 Password match check: raw={}, encoded={}, result={}",
                        user.getPassword(), userInfo.getPassword(), match);
            } else {
                log.warn("❌ User not found in DB: {}", user.getEmail());
            }

            // Gọi authenticate của Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            log.info("✅ Login SUCCESS: email={}", user.getEmail());

            return ResponseEntity.ok(new JwtResponse(
                    userInfo.getId(),
                    jwt,
                    userInfo.getName(),
                    userDetails.getAuthorities()
            ));
        } catch (UsernameNotFoundException e) {
            log.error("❌ Login FAILED - user not found: {}", user.getEmail(), e);
            throw e;
        } catch (BadCredentialsException e) {
            log.error("❌ Login FAILED - bad credentials: email={}, rawPassword={}",
                    user.getEmail(), user.getPassword(), e);
            throw e;
        }
    }
}
