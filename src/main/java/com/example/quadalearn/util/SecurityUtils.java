package com.example.quadalearn.util;

import com.example.quadalearn.dto.user.CustomUserDetails;
import com.example.quadalearn.model.auth.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    private SecurityUtils() {
        // tránh new instance
    }

    /**
     * Lấy Authentication hiện tại trong SecurityContext
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Lấy username (thường là email) của user hiện tại
     */
    public static String getCurrentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }

    /**
     * Lấy UserDetails custom nếu có
     */
    public static UserDetails getCurrentUserDetails() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Kiểm tra xem có user đăng nhập không
     */
    public static boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static User getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUser();
        }
        return null;
    }
}

