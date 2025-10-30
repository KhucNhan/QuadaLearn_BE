package com.example.quadalearn.service.grammar;

import com.example.quadalearn.model.grammar.GrammarExample;

import java.util.List;

public interface IGrammarExampleGennerateService {

    List<GrammarExample> getExamplesByDetailId(Long detailId);

    // Lấy tất cả ví dụ ngữ pháp
    List<GrammarExample> findAll();

    // Tìm ví dụ theo ID
    GrammarExample findById(Long id);

    // Thêm mới ví dụ
    GrammarExample add(GrammarExample example);

    // Xoá ví dụ theo ID
    void delete(Long id);

}
