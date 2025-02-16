package com.example.gyankosh.service;

import com.example.gyankosh.Entity.ModelSetExam;
import com.example.gyankosh.Entity.ModelSetQuestions;
import com.example.gyankosh.Entity.User;
import com.example.gyankosh.Repository.ModelSetExamRepository;
import com.example.gyankosh.dto.ModelSetExamSubmitDto;

import com.example.gyankosh.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ModelSetExamService {
@Autowired
ModelSetExamRepository modelSetExamRepository;
@Autowired
    ModelMapper modelMapper;

    // Fetch the exam set based on examtype and setname
    public List<ModelSetQuestions> getExamSet(String examType, String setName)

    {
//        User user=this.modelMapper.map(userDto,User.class);
//        System.out.println("FirstName: " + user.getFirstName());
//        System.out.println("LastName: " + user.getLastName());
////        user.setFirstName(user.getFirstName());
//        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
//        user.setCreatedTime(LocalDateTime.now());
        ModelSetExam modelSetExam=modelSetExamRepository.findByExamTypeAndSetName(examType,setName);
       List<ModelSetQuestions> modelSetQuestions;
       modelSetQuestions= modelSetExam.getModelSetQuestionsList();
        return modelSetQuestions;




//         modelSetExamRepository.findByExamTypeAndSetName(examType,setName);

    }

    //calculate score based on submitted answers

//    public int calculateScore(ModelSetExamSubmitDto modelSetExamSubmitDto)
//    {
//        ModelSetExam modelSetExam=getExamSet(modelSetExamSubmitDto.getExamType(),modelSetExamSubmitDto.getSetName());
//        int score=0;
//        // Iterate through each answer submitted by the user
//        for (ModelSetExamSubmitDto.AnswerDto answerDto : modelSetExamSubmitDto.getAnswers()) {  //user le deko answer vari answerdto ma basxa ani loop chalxa
//            // Assume that modelSetExam.getModelSetQuestionsList() returns a List<Question>
//            List<ModelSetQuestions> questions = modelSetExam.getModelSetQuestionsList();   //ModelSetExam bata listof question matra extract vaya
//            //ModelSetQuestions ko object create garew
//            ModelSetQuestions matchedQuestion = null;   //suruma matchedQuestion Null rakhama
//
//            // Find the question with the matching ID
//            for (ModelSetQuestions modelSetQuestions :questions ) {   //mathi extract gareko  list of questions lai modelsetQuestions ko object create garera tesma rakhema
//                if (modelSetQuestions.getQid().equals(answerDto.getQid()))  //database bata taner modelsetQuestion ma vako question ko id ra userle submit gareko answerko id same xa xaina
//                {
//                    matchedQuestion=modelSetQuestions;  //yadi xa vana matched question ma  tyo question rakhna
//                    break; // Exit the loop once the question is found
//                }
//            }
//
//            // Check if the question was found
//            if (matchedQuestion != null) {
//                // Check if the user's answer matches the correct answer
//                if (matchedQuestion.getCorrectAnswer().equals(answerDto.getCorrectAnswer())) {
//                    score++;  // Increment score for each correct answer
//                }
//            } else {
//                throw new IllegalArgumentException("Question not found");
//            }
//        }
//
//        return score;  // Return the final score
//    }
}






//    // Step 1: Fetch the exam set based on examType and setName from the repository
//    public ModelSetExam getExamSet(String examType, String setName) {
//        // Fetch the exam set that matches the provided examType and setName
//        // If no matching exam set is found, throw an exception
//        ModelSetExam exam = modelSetExamRepository.findByExamTypeAndSetName(examType, setName);
//        if (exam == null) {
//            throw new IllegalArgumentException("Exam set not found for given examType and setName");
//        }
//        return exam;
//    }
//
//    // Step 2: Calculate the score based on the answers submitted by the user
//    public int calculateScore(ModelSetExamSubmitDto modelSetExamSubmitDto) {
//        // Fetch the exam set (using examType and setName) for which the user is submitting answers
//        ModelSetExam modelSetExam = getExamSet(modelSetExamSubmitDto.getExamType(), modelSetExamSubmitDto.getSetName());
//
//        int score = 0;  // Initialize the score to zero
//
//        // Step 3: Iterate through each answer that the user has submitted
//        for (ModelSetExamSubmitDto.AnswerDto answerDto : modelSetExamSubmitDto.getAnswers()) {
//
//            // Step 4: Extract the list of questions from the fetched exam set
//            List<ModelSetQuestions> questions = modelSetExam.getModelSetQuestionsList();
//
//            // Step 5: Create a variable to store the matching question (initialized as null)
//            ModelSetQuestions matchedQuestion = null;
//
//            // Step 6: Loop through each question in the exam set and look for the question that matches the user's submitted answer (based on question ID)
//            for (ModelSetQuestions modelSetQuestions : questions) {
//                // If the question ID matches the question ID from the user's answer, set the matchedQuestion variable
//                if (modelSetQuestions.getQid().equals(answerDto.getQuestionId())) {
//                    matchedQuestion = modelSetQuestions;  // Save the matching question
//                    break;  // Break out of the loop once the question is found
//                }
//            }
//
//            // Step 7: Check if the matching question was found
//            if (matchedQuestion != null) {
//                // Step 8: Compare the user's submitted answer with the correct answer for the matched question
//                if (matchedQuestion.getCorrectAnswer().equals(answerDto.getCorrectAnswer())) {
//                    score++;  // Increment the score by 1 for each correct answer
//                }
//            } else {
//                // Step 9: If no matching question was found (i.e., question ID is invalid), throw an exception
//                throw new IllegalArgumentException("Question not found for ID: " + answerDto.getQuestionId());
//            }
//        }
//
//        // Step 10: Return the final calculated score
//        return score;
//    }
//}
