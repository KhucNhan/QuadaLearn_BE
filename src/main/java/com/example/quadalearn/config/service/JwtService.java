package com.example.quadalearn.config.service;

import com.example.quadalearn.dto.user.CustomUserDetails;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.auth.Role; // Cần import Role
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Cần import Collectors

@Service
public class JwtService {

    private static final String SECRET_KEY = "123456789987654321123456789987654321123456789";
    // Đơn vị mili-giây (86400000000L mili giây ~ 1000 ngày)
    private static final long EXPIRE_TIME = 86400000000L;

    public String generateTokenLogin(Authentication authentication) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        // 1. Lấy danh sách Authorities (Roles) từ UserDetails
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 2. Tạo Claims và thêm Roles
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles); // ✅ Đã thêm Roles

        return Jwts.builder()
                .setClaims(claims) // Set Claims
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // EXPIRE_TIME là miliseconds, không cần nhân 1000 nếu EXPIRE_TIME đã là miliseconds
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(User user) {

        // 1. Lấy vai trò từ User Entity
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = user.getRoles().stream()
                .map(Role::getName) // Giả định Role.getName() trả về chuỗi "ROLE_USER"
                .collect(Collectors.toList());

        // 2. Thêm vai trò vào Claims
        claims.put("roles", roles); // ✅ Đã thêm Roles

        return Jwts.builder()
                .setClaims(claims) // Set Claims
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // EXPIRE_TIME là miliseconds, không cần nhân 1000 nếu EXPIRE_TIME đã là miliseconds
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ✅ PHƯƠNG THỨC MỚI: Trả về Claims để JwtAuthenticationTokenFilter có thể đọc Roles
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey()) // Sửa: Dùng Key object
                    .build()
                    .parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token -> Message: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token -> Message: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token -> Message: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty -> Message: " + e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // ✅ Sửa: Dùng Key object
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}