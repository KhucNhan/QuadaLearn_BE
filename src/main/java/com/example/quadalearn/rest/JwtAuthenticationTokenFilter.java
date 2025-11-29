package com.example.quadalearn.rest;

import com.example.quadalearn.config.service.JwtService;
import com.example.quadalearn.service.impl.auth.UserService;
import io.jsonwebtoken.Claims; // <-- Cần import Claims
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // <-- Cần import SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List; // <-- Cần import List
import java.util.stream.Collectors; // <-- Cần import Collectors

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (jwt != null && jwtService.validateJwtToken(jwt)) {

                // 1. Lấy tất cả Claims từ JWT (đã sửa trong JwtService)
                Claims claims = jwtService.getAllClaimsFromToken(jwt);
                String email = claims.getSubject();

                // Load user từ email (đảm bảo userDetails không null, nhưng không cần Roles)
                // Dùng phương thức đã sửa (findByEmailWithRoles) trong UserService
                UserDetails userDetails = userService.loadUserByUsername(email);

                // 2. Đọc Roles từ Claims và chuyển đổi sang Authorities
                // ⚠️ Bắt buộc phải cast sang List<String>
                List<String> roles = (List<String>) claims.get("roles");

                // Xử lý trường hợp roles null hoặc rỗng (nếu lỗi JwtService)
                if (roles == null) {
                    roles = List.of(); // Tránh NullPointerException
                }

                List<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // 3. Tạo Authentication object với Authorities lấy từ JWT
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                authorities // ✅ Lấy Authorities từ JWT Claims
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set Authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: " + e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // cắt "Bearer "
        }
        return null;
    }
}