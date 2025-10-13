package com.example.quadalearn.service.impl.grammar;

import com.example.quadalearn.model.grammar.GrammarExample;
import com.example.quadalearn.repository.grammar.IGrammarExampleRepository;
import com.example.quadalearn.repository.grammar.IGrammarTopicRepository;
import com.example.quadalearn.service.grammar.IGrammarExampleGennerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrammarExampleService implements IGrammarExampleGennerateService {
    @Autowired
    private IGrammarExampleRepository iGrammarExampleRepository;

    @Override
    public List<GrammarExample> getExamplesByDetailId(Long detailId) {
        return iGrammarExampleRepository.findByDetailId(detailId);
    }

    @Override
    public List<GrammarExample> findAll() {
        return iGrammarExampleRepository.findAll();
    }

    @Override
    public GrammarExample findById(Long id) {
        return iGrammarExampleRepository.findById(id).get();
    }

    @Override
    public GrammarExample add(GrammarExample example) {
        return iGrammarExampleRepository.save(example);
    }

    @Override
    public void delete(Long id) {
        iGrammarExampleRepository.deleteById(id);

    }


}
