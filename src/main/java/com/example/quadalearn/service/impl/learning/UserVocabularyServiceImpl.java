// src/main/java/com/example/quadalearn/service/impl/UserVocabularyServiceImpl.java
package com.example.quadalearn.service.impl.learning;

import com.example.quadalearn.model.learning.UserVocabulary;
import com.example.quadalearn.repository.learning.UserVocabularyRepository;
import com.example.quadalearn.service.learning.IUserVocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserVocabularyServiceImpl implements IUserVocabularyService {
    private final UserVocabularyRepository userVocabularyRepository;

    @Transactional
    @Override
    public UserVocabulary create(UserVocabulary userVocabulary) {
        return userVocabularyRepository.save(userVocabulary);
    }

    @Transactional
    @Override
    public UserVocabulary update(UserVocabulary userVocabulary) {
        return userVocabularyRepository.save(userVocabulary);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userVocabularyRepository.deleteById(id);
    }

    @Override
    public Optional<UserVocabulary> findById(Long id) {
        return userVocabularyRepository.findById(id);
    }

    @Override
    public List<UserVocabulary> findAll() {
        return userVocabularyRepository.findAll();
    }

    @Override
    public List<UserVocabulary> findByUserId(Long userId) {
        return userVocabularyRepository.findByUser_Id(userId);
    }

    @Override
    public boolean exists(Long userId, Long vocabularyId) {
        return userVocabularyRepository.existsByUser_IdAndVocabulary_Id(userId, vocabularyId);
    }
}
