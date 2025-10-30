package com.example.quadalearn.service.impl.grammar;

import com.example.quadalearn.model.grammar.GrammarTopic;
import com.example.quadalearn.repository.grammar.IGrammarTopicRepository;
import com.example.quadalearn.service.grammar.IGrammarTopicGennerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrammarTopicService implements IGrammarTopicGennerateService {
    @Autowired
    private IGrammarTopicRepository iGrammarTopicRepository;
    @Override
    public List<GrammarTopic> findAll() {
        return iGrammarTopicRepository.findAll();
    }

    @Override
    public GrammarTopic findById(Long id) {
        return iGrammarTopicRepository.findById(id).get();
    }

    @Override
    public GrammarTopic add(GrammarTopic topic) {
        return iGrammarTopicRepository.save(topic);
    }

    @Override
    public void delete(Long id) {
        iGrammarTopicRepository.deleteById(id);
    }




}
