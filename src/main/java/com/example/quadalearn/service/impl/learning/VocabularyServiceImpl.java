// src/main/java/com/example/quadalearn/service/impl/VocabularyServiceImpl.java
package com.example.quadalearn.service.impl.learning;

import com.example.quadalearn.model.learning.Vocabulary;
import com.example.quadalearn.repository.learning.VocabularyRepository;
import com.example.quadalearn.service.learning.IVocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VocabularyServiceImpl implements IVocabularyService {
    private final VocabularyRepository vocabularyRepository;

    @Transactional
    @Override
    public Vocabulary create(Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }

    @Transactional
    @Override
    public Vocabulary update(Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        vocabularyRepository.deleteById(id);
    }

    @Override
    public Optional<Vocabulary> findById(Long id) {
        return vocabularyRepository.findById(id);
    }

    @Override
    public List<Vocabulary> findAll() {
        return vocabularyRepository.findAll();
    }

    @Override
    public List<Vocabulary> findByLevel(String level) {
        return vocabularyRepository.findByLevel(level);
    }

    @Override
    public List<Vocabulary> searchByWord(String keyword) {
        return vocabularyRepository.findByWordContainingIgnoreCase(keyword);
    }
}
