package com.example.quadalearn.service.impl.auth;

import com.example.quadalearn.dto.UserDTO;
import com.example.quadalearn.dto.user.CustomUserDetails;
import com.example.quadalearn.model.auth.User;
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

    // --- CÁC PHƯƠNG THỨC IMPOSED TỪ IUserService ---

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
        // Có thể dùng findByIdWithRoles() ở đây nếu UserDTO cần Roles
        Optional<User> user = iUserRepository.findById(id);
        return user.map(this::toDTO).orElse(null);
    }

    /**
     * PHƯƠNG THỨC ĐÃ SỬA: Dùng cho JWT Filter/Security để đảm bảo Roles được tải.
     */
    @Override
    public Optional<User> findByEmailWithRoles(String email) {
        // ✅ GỌI PHƯƠNG THỨC JOIN FETCH MỚI
        return iUserRepository.findByEmailWithRoles(email);
    }

    @Override
    public User findByEmail(String email) {
        // Phương thức này có thể chỉ dùng cho logic kiểm tra tồn tại, không cần Roles
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

    /**
     * PHƯƠNG THỨC ĐÃ SỬA: Dùng cho Controller để lấy User và Roles.
     */
    @Override
    public User getUser(Long id) {
        // ✅ GỌI PHƯƠNG THỨC JOIN FETCH THEO ID MỚI
        return iUserRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Long id, User updated) {
        User user = getUser(id);
        // Lưu ý: getUser() đã dùng findByIdWithRoles, đảm bảo Roles có sẵn.

        user.setName(updated.getName());
        user.setGender(updated.getGender());
        user.setEmail(updated.getEmail());

        return iUserRepository.save(user);
    }

    // ... (các phương thức updateUserAdmin và updateImage giữ nguyên) ...

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

    // --- PHƯƠNG THỨC QUAN TRỌNG NHẤT: UserDetailsService ---

    /**
     * PHƯƠNG THỨC ĐÃ SỬA: Load UserDetails cho Spring Security.
     */
// Trong file UserService.java

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Gọi phương thức JOIN FETCH để User Entity có sẵn Roles
        User user = findByEmailWithRoles(email) // <<< Sử dụng phương thức mới
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Tạo đối tượng UserDetails (CustomUserDetails) chứa thông tin và Roles
        return new CustomUserDetails(user);
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user);
    }
}