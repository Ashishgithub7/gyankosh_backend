package com.example.gyankosh.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;
    private String difficulty; // kuna level ko question show garna: "easy", "medium", or "hard"
    private String correctAnswer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String feedback;
    private String course;

}
