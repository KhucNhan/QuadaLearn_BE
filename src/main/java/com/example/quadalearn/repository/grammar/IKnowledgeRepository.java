package com.example.quadalearn.repository.grammar;

import com.example.quadalearn.model.grammar.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKnowledgeRepository extends JpaRepository<Knowledge, Long> {
    @Query("SELECT k FROM Knowledge k WHERE k.topicType.id = :typeId")
    List<Knowledge> findByTopicTypeId(@Param("typeId") Long typeId);

    @Query("SELECT k FROM Knowledge k WHERE k.lesson.id = :lessonId")
    List<Knowledge> findByLessonId(@Param("lessonId") Long lessonId);

}
