package com.example.quadalearn.service.impl.grammar;

import com.example.quadalearn.model.grammar.Knowledge;
import com.example.quadalearn.repository.grammar.IKnowledgeRepository;
import com.example.quadalearn.service.grammar.IKnowledgeGennerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeService implements IKnowledgeGennerateService {
    @Autowired
    private IKnowledgeRepository iKnowledgeRepository;

    @Override
    public List<Knowledge> findAll() {
        return iKnowledgeRepository.findAll();
    }

    @Override
    public Knowledge findById(Long id) {
        return iKnowledgeRepository.findById(id).get();
    }

    @Override
    public Knowledge add(Knowledge knowledge) {
        return iKnowledgeRepository.save(knowledge);
    }

    @Override
    public void delete(Long id) {
        iKnowledgeRepository.deleteById(id);
    }

    @Override
    public List<Knowledge> getKnowledgeByTypeId(Long typeId) {
        return iKnowledgeRepository.findByTopicTypeId(typeId);
    }

}
