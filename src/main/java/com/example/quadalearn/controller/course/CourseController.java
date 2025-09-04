package com.example.quadalearn.controller.course;

import com.example.quadalearn.model.learning.Course;
import com.example.quadalearn.service.impl.learning.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;

    // GET all
    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.findById(id);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping("")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.create(course);
        return ResponseEntity.ok(savedCourse);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Optional<Course> existing = courseService.findById(id);
        if (existing.isPresent()) {
            course.setId(id);
            Course updated = courseService.create(course);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        Optional<Course> course = courseService.findById(id);
        if (course.isPresent()) {
            courseService.delete(id);
            return ResponseEntity.ok("Xóa thành công course với ID: " + id);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy course với ID: " + id);
        }
    }


    @GetMapping("/level")
    public ResponseEntity<List<Course>> getCoursesByLevel(@RequestParam String level) {
        List<Course> courses = courseService.findByLevel(level);
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 nếu không có kết quả
        }
        return ResponseEntity.ok(courses); // 200 OK với danh sách course
    }

}
