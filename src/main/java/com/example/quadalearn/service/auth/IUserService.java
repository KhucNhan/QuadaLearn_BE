package com.example.quadalearn.service.auth;

import com.example.quadalearn.dto.UserDTO;
import com.example.quadalearn.model.auth.User;

import java.util.List;
import java.util.Optional;


public interface IUserService {
    List<UserDTO> findAll();

    UserDTO findById(Long id);

    // ✅ PHƯƠNG THỨC MỚI: Load User bằng email và Roles (dùng cho JWT Filter/UserDetailsService)
    Optional<User> findByEmailWithRoles(String email);

    // Phương thức cũ (Chỉ dùng cho các logic không cần Roles ngay):
    User findByEmail(String email);

    User add(User user);

    void delete(Long id);

    User save(User user);

    User getUser(Long id);

    User updateUser(Long id, User updated);

    User updateUserAdmin(Long id, User updated);

    User updateImage(Long id, String url);
}