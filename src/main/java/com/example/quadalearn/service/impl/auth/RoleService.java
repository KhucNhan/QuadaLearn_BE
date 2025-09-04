package com.example.quadalearn.service.impl.auth;


import com.example.quadalearn.model.auth.Role;
import com.example.quadalearn.repository.auth.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
