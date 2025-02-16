package com.example.gyankosh.service;

import com.example.gyankosh.Entity.Model;
import com.example.gyankosh.Entity.ModelSetExam;
import com.example.gyankosh.Entity.ModelSetQuestions;
import com.example.gyankosh.Repository.ModelRepo;
import com.example.gyankosh.Repository.ModelSetExamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ModelService {
        @Autowired
    ModelRepo modelRepo;
        @Autowired
        ModelMapper modelMapper;

    public List<Model> getExamSet(String course, String setName)

    {
        return  modelRepo.findByCourseAndSetName(course,setName);



    }

}
