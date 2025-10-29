package com.example.quadalearn.repository.read;

import com.example.quadalearn.model.read.ReadingVocabulary;
import com.example.quadalearn.model.read.ReadingPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingVocabularyRepository extends JpaRepository<ReadingVocabulary, Long> {
    List<ReadingVocabulary> findByPassage(ReadingPassage passage);
    List<ReadingVocabulary> findByPassageId(Long passageId);
}