package com.example.quadalearn.service.auth;

import com.example.quadalearn.dto.UserDTO;
import com.example.quadalearn.model.auth.User;

import java.util.List;


public interface IUserService {
    List<UserDTO> findAll();

    UserDTO findById(Long id);

    User findByEmail(String email);

    User add(User user);

    void delete(Long id);

    User save(User user);

    User getUser(Long id);

    User updateUser(Long id, User updated);

    User updateImage(Long id, String url);
}
