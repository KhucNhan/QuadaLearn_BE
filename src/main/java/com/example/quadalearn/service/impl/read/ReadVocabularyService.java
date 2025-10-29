package com.example.quadalearn.service.impl.read;

import com.example.quadalearn.model.read.ReadingVocabulary;
import com.example.quadalearn.repository.read.ReadingVocabularyRepository;
import com.example.quadalearn.service.read.IReadVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadVocabularyService implements IReadVocabularyService {

    @Autowired
    private ReadingVocabularyRepository repository;

    @Override
    public List<ReadingVocabulary> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ReadingVocabulary> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ReadingVocabulary> findByPassageId(Long passageId) {
        return repository.findByPassageId(passageId);
    }

    @Override
    public ReadingVocabulary save(ReadingVocabulary vocabulary) {
        return repository.save(vocabulary);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
