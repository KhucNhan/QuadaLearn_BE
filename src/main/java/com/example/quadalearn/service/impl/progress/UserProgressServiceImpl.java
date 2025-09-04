// src/main/java/com/example/quadalearn/service/impl/UserProgressServiceImpl.java
package com.example.quadalearn.service.impl.progress;

import com.example.quadalearn.model.progress.UserProgress;
import com.example.quadalearn.repository.progress.UserProgressRepository;
import com.example.quadalearn.service.progress.IUserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProgressServiceImpl implements IUserProgressService {
    private final UserProgressRepository userProgressRepository;

    @Transactional
    @Override
    public UserProgress create(UserProgress userProgress) {
        return userProgressRepository.save(userProgress);
    }

    @Transactional
    @Override
    public UserProgress update(UserProgress userProgress) {
        return userProgressRepository.save(userProgress);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userProgressRepository.deleteById(id);
    }

    @Override
    public Optional<UserProgress> findById(Long id) {
        return userProgressRepository.findById(id);
    }

    @Override
    public List<UserProgress> findAll() {
        return userProgressRepository.findAll();
    }

    @Override
    public List<UserProgress> findByUserId(Long userId) {
        return userProgressRepository.findByUser_Id(userId);
    }

    @Override
    public List<UserProgress> findByCourseId(Long courseId) {
        return userProgressRepository.findByCourse_Id(courseId);
    }

    @Override
    public Optional<UserProgress> findByUserIdAndCourseId(Long userId, Long courseId) {
        return userProgressRepository.findByUser_IdAndCourse_Id(userId, courseId);
    }
}
