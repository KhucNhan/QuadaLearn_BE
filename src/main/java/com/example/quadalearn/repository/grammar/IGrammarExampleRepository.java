package com.example.quadalearn.repository.grammar;

import com.example.quadalearn.model.grammar.GrammarExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGrammarExampleRepository extends JpaRepository<GrammarExample, Long> {
    List<GrammarExample> findByDetailId(Long detailId);
}
