package com.example.gyankosh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelSetExamSubmitDto {
    private String examType;
    private String setName;
    private List<AnswerDto> answers;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    //submitted answers
    public static class AnswerDto {
        private Long qid;
        private String correctAnswer;  // Correct answer option text

        // Getters, Setters
    }
}
