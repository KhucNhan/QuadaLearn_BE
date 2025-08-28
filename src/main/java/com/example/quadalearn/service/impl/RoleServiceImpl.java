// src/main/java/com/example/quadalearn/service/impl/RoleServiceImpl.java
package com.example.quadalearn.service.impl;

import com.example.quadalearn.model.Role;
import com.example.quadalearn.repository.RoleRepository;
import com.example.quadalearn.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
