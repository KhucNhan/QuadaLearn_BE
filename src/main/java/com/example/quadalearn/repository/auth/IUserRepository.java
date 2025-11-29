package com.example.quadalearn.repository.auth;

import com.example.quadalearn.model.auth.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    // ✅ PHƯƠNG THỨC MỚI: Load User bằng email VÀ bắt buộc tải Roles (Sử dụng cho Security)
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);

    // ✅ PHƯƠNG THỨC MỚI: Load User bằng ID VÀ bắt buộc tải Roles (Sử dụng cho API Profile)
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findByIdWithRoles(@Param("id") Long id);

    // Phương thức cũ (Không nên dùng cho Security):
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.lastLoginAt = :lastLoginAt WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") Long userId,
                         @Param("lastLoginAt") LocalDateTime lastLoginAt);


    @Query("SELECT DISTINCT u FROM User u " +
            "WHERE u.lastLoginAt IS NOT NULL " +
            "AND u.lastLoginAt < :cutoffDate " +
            "ORDER BY u.lastLoginAt ASC")
    List<User> findUsersInactiveSince(@Param("cutoffDate") LocalDateTime cutoffDate);
}