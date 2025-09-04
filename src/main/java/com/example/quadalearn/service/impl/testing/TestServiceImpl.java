// src/main/java/com/example/quadalearn/service/impl/TestServiceImpl.java
package com.example.quadalearn.service.impl.testing;

import com.example.quadalearn.model.testing.Test;
import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.repository.testing.TestRepository;
import com.example.quadalearn.service.testing.ITestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements ITestService {
    private final TestRepository testRepository;

    @Transactional
    @Override
    public Test create(Test test) {
        return testRepository.save(test);
    }

    @Transactional
    @Override
    public Test update(Test test) {
        return testRepository.save(test);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        testRepository.deleteById(id);
    }

    @Override
    public Optional<Test> findById(Long id) {
        return testRepository.findById(id);
    }

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public List<Test> findByCreator(User creator) {
        return testRepository.findByCreatedBy(creator);
    }

    @Override
    public List<Test> findByType(String type) {
        return testRepository.findByType(type);
    }
}
