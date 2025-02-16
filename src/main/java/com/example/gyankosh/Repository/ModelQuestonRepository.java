package com.example.gyankosh.Repository;

import com.example.gyankosh.Entity.ModelQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelQuestonRepository extends JpaRepository<ModelQuestion,Long> {
  List<ModelQuestion> findByCourseAndSubcourse(String course, String subcourse);
}
