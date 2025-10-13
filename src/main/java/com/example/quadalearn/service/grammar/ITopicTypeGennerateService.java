package com.example.quadalearn.service.grammar;

import com.example.quadalearn.model.grammar.TopicType;

import java.util.List;

public interface ITopicTypeGennerateService {

    // Lấy tất cả TopicType
    List<TopicType> findAll();

    // Tìm theo ID
    TopicType findById(Long id);

    // Thêm mới
    TopicType add(TopicType topicType);

    // Xoá theo ID
    void delete(Long id);

}
