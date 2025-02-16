package com.example.gyankosh.Repository;

import com.example.gyankosh.Entity.ModelSetExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelSetExamRepository extends JpaRepository<ModelSetExam,Long> {
    ModelSetExam findByExamTypeAndSetName(String examType,String setName);
}
