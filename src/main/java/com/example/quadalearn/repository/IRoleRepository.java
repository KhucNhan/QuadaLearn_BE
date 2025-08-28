package com.example.quadalearn.repository;

import com.example.quadalearn.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();
}
