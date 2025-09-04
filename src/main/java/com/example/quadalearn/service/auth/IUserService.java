package com.example.quadalearn.service.auth;

import com.example.quadalearn.dto.UserDTO;
import com.example.quadalearn.model.auth.User;

import java.util.List;


public interface IUserService {
    List<UserDTO> findAll();

    UserDTO findById(Long id);

    User findByEmail(String email);

    boolean add(User user);

    void delete(Long id);
}
