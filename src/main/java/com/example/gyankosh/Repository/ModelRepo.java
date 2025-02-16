package com.example.gyankosh.Repository;

import com.example.gyankosh.Entity.Model;
import com.example.gyankosh.Entity.ModelQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepo extends JpaRepository<Model,Long> {
    List<Model> findByCourseAndSetName(String course, String setName);

}
