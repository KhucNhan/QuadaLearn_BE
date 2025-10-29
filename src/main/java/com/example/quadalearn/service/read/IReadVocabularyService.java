package com.example.quadalearn.service.read;

import com.example.quadalearn.model.read.ReadingVocabulary;

import java.util.List;
import java.util.Optional;

public interface IReadVocabularyService {
    List<ReadingVocabulary> findAll();
    Optional<ReadingVocabulary> findById(Long id);
    List<ReadingVocabulary> findByPassageId(Long passageId);
    ReadingVocabulary save(ReadingVocabulary vocabulary);
    void deleteById(Long id);
}
