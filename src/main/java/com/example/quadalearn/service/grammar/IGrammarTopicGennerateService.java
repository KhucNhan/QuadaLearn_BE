package com.example.quadalearn.service.grammar;

import com.example.quadalearn.model.grammar.GrammarTopic;

import java.util.List;

public interface IGrammarTopicGennerateService {

    // Lấy tất cả topic
    List<GrammarTopic> findAll();

    // Tìm theo ID
    GrammarTopic findById(Long id);

    // Thêm mới 1 GrammarTopic
    GrammarTopic add(GrammarTopic topic);

    // Xoá theo ID
    void delete(Long id);

}
