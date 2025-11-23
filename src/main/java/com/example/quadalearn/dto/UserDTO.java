package com.example.quadalearn.dto;

import com.example.quadalearn.model.auth.Role;
import com.example.quadalearn.model.auth.User;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String currentLevel;
    private String goal;
    private String image;
    private String background;
    private Set<Role> roles;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String gender, String currentLevel,
                   String goal, String image, String background, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.currentLevel = currentLevel;
        this.goal = goal;
        this.image = image;
        this.background = background;
        this.roles = roles;
    }

    // ✅ Constructor để chuyển trực tiếp từ Entity sang DTO
    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.gender = user.getGender() != null ? user.getGender().name() : null;
        this.currentLevel = user.getCurrentLevel();
        this.goal = user.getGoal();
        this.image = user.getImage();
        this.background = user.getBackground();
        this.roles = user.getRoles();
    }

    // 🔹 Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
