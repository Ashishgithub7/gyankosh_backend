package com.example.gyankosh.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelSetExamDto {
    private Long qid;
    private String question_text;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
}
