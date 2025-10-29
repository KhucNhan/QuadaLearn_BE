package com.example.quadalearn.service.read;

import com.example.quadalearn.model.read.ReadingPassage;

import java.util.List;
import java.util.Optional;

public interface IReadPassageService {
    List<ReadingPassage> findAll();
    Optional<ReadingPassage> findById(Long id);
    ReadingPassage save(ReadingPassage passage);
    void deleteById(Long id);
}
