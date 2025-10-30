package com.example.quadalearn.repository.grammar;

import com.example.quadalearn.model.grammar.TopicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITopicTypeRepository extends JpaRepository<TopicType, Long> {

    List<TopicType> findByTopic_Id(Long topicId);
}
