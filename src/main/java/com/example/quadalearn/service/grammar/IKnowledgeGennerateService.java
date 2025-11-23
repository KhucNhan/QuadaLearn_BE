package com.example.quadalearn.service.grammar;

import com.example.quadalearn.model.grammar.Knowledge;

import java.util.List;

public interface IKnowledgeGennerateService {

    // Lấy tất cả kiến thức ngữ pháp
    List<Knowledge> findAll();

    // Tìm theo ID
    Knowledge findById(Long id);

    // Thêm mới một kiến thức
    Knowledge add(Knowledge knowledge);

    // Xoá kiến thức theo ID
    void delete(Long id);

    List<Knowledge> getKnowledgeByTypeId(Long typeId);

    List<Knowledge> getKnowledgeByLessonId(Long lessonId);
}
