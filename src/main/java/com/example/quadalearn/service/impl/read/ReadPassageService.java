package com.example.quadalearn.service.impl.read;

import com.example.quadalearn.model.read.ReadingPassage;
import com.example.quadalearn.repository.read.ReadingPassageRipository;
import com.example.quadalearn.service.read.IReadPassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadPassageService implements IReadPassageService {

    @Autowired
    private ReadingPassageRipository readingPassageRipository;

    @Override
    public List<ReadingPassage> findAll() {
        return readingPassageRipository.findAll();
    }

    @Override
    public Optional<ReadingPassage> findById(Long id) {
        return readingPassageRipository.findById(id);
    }

    @Override
    public ReadingPassage save(ReadingPassage passage) {
        return readingPassageRipository.save(passage);
    }

    @Override
    public void deleteById(Long id) {
        readingPassageRipository.deleteById(id);
    }
}