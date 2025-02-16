package com.example.gyankosh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {
//    private Long id;
    private String questionText;
    private String difficulty; // kuna level ko question show garna: "easy", "medium", or "hard"
    private String correctAnswer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String feedback;
    private boolean isQuizComplete;
    private String finalFeedback;

    public QuestionResponseDto(String questionText, String currentDifficulty, String correctAnswer, String optionA, String optionB, String optionC, String optionD, String feedback, String finalFeedback, boolean isQuizComplete) {
        this.difficulty=currentDifficulty;
        this.questionText=questionText;
        this.optionA=optionA;
        this.optionB=optionB;
        this.optionC=optionC;
        this.optionD=optionD;
        this.correctAnswer=correctAnswer;
        this.feedback=feedback;
        this.isQuizComplete=isQuizComplete;
        this.finalFeedback=finalFeedback;
    }

    //yo banaunu ra mathi @AllArgsConstuctor lekhnu eutai ho
//    public QuestionResponseDto(String currentDifficulty, String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer, String feedback, boolean isQuizComplete) {
//        this.difficulty=currentDifficulty;
//        this.questionText=questionText;
//        this.optionA=optionA;
//        this.optionB=optionB;
//        this.optionC=optionC;
//        this.optionD=optionD;
//        this.correctAnswer=correctAnswer;
//        this.feedback=feedback;
//        this.isQuizComplete=isQuizComplete;
//
//
//    }
}
