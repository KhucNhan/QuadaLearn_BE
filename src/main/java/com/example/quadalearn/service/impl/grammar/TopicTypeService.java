package com.example.quadalearn.service.impl.grammar;

import com.example.quadalearn.model.grammar.TopicType;
import com.example.quadalearn.repository.grammar.ITopicTypeRepository;
import com.example.quadalearn.service.grammar.ITopicTypeGennerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicTypeService implements ITopicTypeGennerateService {
    @Autowired
    private ITopicTypeRepository iTopicTypeRepository;

    public List<TopicType> getTypesByTopicId(Long topicId) {
        return iTopicTypeRepository.findByTopic_Id(topicId);
    }

    @Override
    public List<TopicType> findAll() {
        return iTopicTypeRepository.findAll();
    }

    @Override
    public TopicType findById(Long id) {
        return iTopicTypeRepository.findById(id).get();
    }

    @Override
    public TopicType add(TopicType topicType) {
        return iTopicTypeRepository.save(topicType);
    }

    @Override
    public void delete(Long id) {
        iTopicTypeRepository.deleteById(id);

    }

}
