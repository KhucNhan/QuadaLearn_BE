package com.example.quadalearn.controller.grammar;

import com.example.quadalearn.model.grammar.GrammarExample;
import com.example.quadalearn.service.grammar.IGrammarExampleGennerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grammar-examples")
public class GrammarExampleController {

    private final IGrammarExampleGennerateService grammarExampleService;




    @GetMapping("/by-detail/{detailId}")
    public ResponseEntity<List<GrammarExample>> getExamplesByDetailId(@PathVariable Long detailId) {
        List<GrammarExample> examples = grammarExampleService.getExamplesByDetailId(detailId);
        return ResponseEntity.ok(examples);
    }

    @Autowired
    public GrammarExampleController(IGrammarExampleGennerateService grammarExampleService) {
        this.grammarExampleService = grammarExampleService;
    }
    // Lấy tất cả ví dụ
    @GetMapping
    public ResponseEntity<List<GrammarExample>> getAllExamples() {
        return ResponseEntity.ok(grammarExampleService.findAll());
    }

    // Lấy ví dụ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<GrammarExample> getExampleById(@PathVariable Long id) {
        GrammarExample example = grammarExampleService.findById(id);
        if (example == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(example);
    }

    // Thêm ví dụ mới
    @PostMapping
    public ResponseEntity<GrammarExample> createExample(@RequestBody GrammarExample example) {
        GrammarExample created = grammarExampleService.add(example);
        return ResponseEntity.ok(created);
    }

    // Xoá ví dụ theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExample(@PathVariable Long id) {
        grammarExampleService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
