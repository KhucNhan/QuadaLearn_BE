package com.example.quadalearn.repository.grammar;

import com.example.quadalearn.model.grammar.GrammarTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrammarTopicRepository extends JpaRepository<GrammarTopic, Long> {
}
