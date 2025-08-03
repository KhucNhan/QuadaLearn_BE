package com.codegym.springbootjwtdemo.repository;

import com.codegym.springbootjwtdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
}
