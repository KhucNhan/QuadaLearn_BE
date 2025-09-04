package com.example.quadalearn.controller.course.lesson;

import com.example.quadalearn.model.learning.Lesson;
import com.example.quadalearn.service.learning.ILessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LessonController {

    private final ILessonService lessonService;

    // Lấy danh sách tất cả bài học
    @GetMapping("")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        List<Lesson> lessons = lessonService.findAll();
        return ResponseEntity.ok(lessons);
    }

    // Lấy bài học theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLessonById(@PathVariable Long id) {
        return lessonService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới bài học
    @PostMapping("")
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        Lesson created = lessonService.create(lesson);
        return ResponseEntity.ok(created);
    }

    // Cập nhật bài học
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        if (!lessonService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        lesson.setId(id); // đảm bảo ID được gán đúng
        Lesson updated = lessonService.update(lesson);
        return ResponseEntity.ok(updated);
    }

    // Xóa bài học
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        if (!lessonService.findById(id).isPresent()) {
            return ResponseEntity.status(404).body("Không tìm thấy lesson với ID: " + id);
        }
        lessonService.delete(id);
        return ResponseEntity.ok("Xóa thành công bài học với ID: " + id);
    }

    // Lấy bài học theo courseId
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Lesson>> getLessonsByCourseId(@PathVariable Long courseId) {
        List<Lesson> lessons = lessonService.findByCourseId(courseId);
        return ResponseEntity.ok(lessons);
    }

    // Lấy bài học theo knowledgeTag
    @GetMapping("/tag")
    public ResponseEntity<List<Lesson>> getLessonsByKnowledgeTag(@RequestParam String tag) {
        List<Lesson> lessons = lessonService.findByKnowledgeTag(tag);
        return ResponseEntity.ok(lessons);
    }
}
