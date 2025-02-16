package com.example.gyankosh.controller;

import com.example.gyankosh.Entity.ModelQuestion;
import com.example.gyankosh.service.ModelQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/modelquiz")
public class AskController {
    @Autowired
    public ModelQuestionService modelQuestionService;
    private String currentCourse;
    private String subCourse;;
    private ModelQuestion currentQuestion;

        @GetMapping("/try")
    public String tryyourself()
    {
        return "welcome to controller";
    }
    @PostMapping("/start")
    public ModelQuestion startQuiz(@RequestParam String course)
    {
        this.currentCourse=course;
        this.subCourse=modelQuestionService.nextSubCourse(course);
        this.currentQuestion= modelQuestionService.findByCourseSubcourse(currentCourse,subCourse);
        return currentQuestion;
    }
}
