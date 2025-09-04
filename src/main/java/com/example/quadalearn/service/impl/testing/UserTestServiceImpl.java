// src/main/java/com/example/quadalearn/service/impl/UserTestServiceImpl.java
package com.example.quadalearn.service.impl.testing;

import com.example.quadalearn.model.testing.UserTest;
import com.example.quadalearn.repository.testing.UserTestRepository;
import com.example.quadalearn.service.testing.IUserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTestServiceImpl implements IUserTestService {
    private final UserTestRepository userTestRepository;

    @Transactional
    @Override
    public UserTest create(UserTest userTest) {
        return userTestRepository.save(userTest);
    }

    @Transactional
    @Override
    public UserTest update(UserTest userTest) {
        return userTestRepository.save(userTest);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userTestRepository.deleteById(id);
    }

    @Override
    public Optional<UserTest> findById(Long id) {
        return userTestRepository.findById(id);
    }

    @Override
    public List<UserTest> findAll() {
        return userTestRepository.findAll();
    }

    @Override
    public List<UserTest> findByUserId(Long userId) {
        return userTestRepository.findByUser_Id(userId);
    }

    @Override
    public List<UserTest> findByTestId(Long testId) {
        return userTestRepository.findByTest_Id(testId);
    }

    @Override
    public Optional<UserTest> findLatestByUserId(Long userId) {
        return userTestRepository.findTop1ByUser_IdOrderByIdDesc(userId);
    }
}
