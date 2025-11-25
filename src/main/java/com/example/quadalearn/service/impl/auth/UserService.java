package com.example.quadalearn.service.impl.auth;

import com.example.quadalearn.dto.UserDTO;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.auth.UserPrinciple;
import com.example.quadalearn.repository.auth.IUserRepository;
import com.example.quadalearn.service.auth.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User u : iUserRepository.findAll()) {
            userDTOS.add(toDTO(u));
        }
        return userDTOS;
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = iUserRepository.findById(id);
        return user.map(this::toDTO).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return iUserRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User add(User user) {
        if (iUserRepository.findByEmail(user.getEmail()).isPresent()) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return iUserRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        iUserRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return iUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Long id, User updated) {
        User user = getUser(id);

        user.setName(updated.getName());
        user.setGender(updated.getGender());
        user.setEmail(updated.getEmail());

        return iUserRepository.save(user);
    }

    @Override
    public User updateUserAdmin(Long id, User updated) {
        User user = getUser(id);

        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        user.setGender(updated.getGender());
        user.setCurrentLevel(updated.getCurrentLevel());
        user.setGoal(updated.getGoal());
        user.setImage(updated.getImage());
        user.setBackground(updated.getBackground());
        user.setRoles(updated.getRoles()); // cập nhật role nếu cần

        return iUserRepository.save(user);
    }


    @Override
    public User updateImage(Long id, String url) {
        User user = getUser(id);
        user.setImage(url); // lưu avatar
        return iUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return UserPrinciple.build(user);
    }


    public UserDTO toDTO(User user) {
        return new UserDTO(user);
    }
}
