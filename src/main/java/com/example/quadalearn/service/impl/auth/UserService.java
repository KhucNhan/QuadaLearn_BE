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
    public boolean add(User user) {
        if (iUserRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        iUserRepository.save(user);
        return true;
    }

    @Override
    public void delete(Long id) {
        iUserRepository.deleteById(id);
    }
    /**
     * Spring Security mặc định vẫn gọi method này khi login,
     * nên ta sẽ ánh xạ username -> email để đồng bộ.
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("🔍 Found user in DB: " + user.getEmail()
                + " | password hash: " + user.getPassword());

        return UserPrinciple.build(user);
    }



    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getRoles());
    }
}
