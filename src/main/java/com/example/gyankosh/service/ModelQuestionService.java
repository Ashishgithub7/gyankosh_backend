package com.example.gyankosh.service;

import com.example.gyankosh.Entity.ModelQuestion;
import com.example.gyankosh.Repository.ModelQuestonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ModelQuestionService {
    @Autowired
    public ModelQuestonRepository modelQuestonRepository;
    private int questionCount=0;
    private Set<Long> askedQuestionIds = new HashSet<>(); // Track asked question IDs
//    //1.Fetch questions based on course and their subourese(subject)
    public ModelQuestion findByCourseSubcourse(String course, String subcourse)
    {
        List<ModelQuestion> modelQuestions=modelQuestonRepository.findByCourseAndSubcourse(course,subcourse);
        //yadi askedQuestionIds ma model question(q) ko id xa vanew remove hunxa
        modelQuestions.removeIf(q -> askedQuestionIds.contains(q.getId()));
        Collections.shuffle(modelQuestions);
        System.out.println("axa yeha");
        return modelQuestions.isEmpty() ? null: modelQuestions.get(0);
    }

    //2.Determine the subject based on the course and question count
    public String nextSubCourse(String course) {
        switch (course) {
            case "IOE":
                if (questionCount <= 25) {
                    return "physics";
                } else if (questionCount > 25 && questionCount <= 50) {
                    return "chemistry";
                } else {
                    return "math";
                }

            case "IOM":
                if (questionCount <= 20) {
                    return "physics";
                } else if (questionCount > 20 && questionCount <= 40) {
                    return "chemistry";
                } else if (questionCount > 40 && questionCount <= 60) {
                    return "biology";
                } else {
                    return "math";
                }

            case "Loksewa":
                if (questionCount <= 25) {
                    return "aptitude";
                } else {
                    return "generalKnowledge";
                }

            default:
                return "Unknown course";
        }
    }

}
