package com.example.gyankosh.controller;

import com.example.gyankosh.Entity.Model;
import com.example.gyankosh.Entity.ModelSetQuestions;
import com.example.gyankosh.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz1")
public class ModelController {
    @Autowired
    ModelService modelService;
    @GetMapping("/start")
    public ResponseEntity<?> getExamSet(@RequestParam String course, @RequestParam String setName) {
      List<Model> setExam = modelService.getExamSet(course, setName);
//        System.out.println("JSON Response:"+new Gson().to);
        return ResponseEntity.ok(setExam);
    }
    @GetMapping("/try")
    public String tryyourself()
    {
        return "welcome to controller";
    }
}

