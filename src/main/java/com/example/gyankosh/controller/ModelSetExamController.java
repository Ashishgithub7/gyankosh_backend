package com.example.gyankosh.controller;

import com.example.gyankosh.Entity.ModelSetExam;
import com.example.gyankosh.Entity.ModelSetQuestions;
import com.example.gyankosh.dto.ModelSetExamDto;
import com.example.gyankosh.service.ModelSetExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/model")
public class ModelSetExamController {
    @Autowired
    private ModelSetExamService modelSetExamService;
    // Fetch an examType and setName
    @GetMapping("/start")
    public List<ModelSetQuestions> getExamSet(@RequestParam String course, @RequestParam String setName) {
       List<ModelSetQuestions>setExam = modelSetExamService.getExamSet(course, setName);
       return setExam;

//      return ResponseEntity.ok(setExam);
    }




//        @GetMapping("/try")
//    public String tryyourself()
//    {
//        return "welcome to controller";
//    }
}
