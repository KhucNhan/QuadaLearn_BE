package com.example.quadalearn.repository.read;

import com.example.quadalearn.model.read.ReadingPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingPassageRipository extends JpaRepository<ReadingPassage, Long> {
}