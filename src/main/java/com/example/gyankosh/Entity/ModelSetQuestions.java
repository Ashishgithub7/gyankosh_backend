package com.example.gyankosh.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class ModelSetQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qid;
    @Column(nullable = false)
    private String question_text;
    @Column(nullable = false)
    private String option1;  // Option text
    @Column(nullable = false)
    private String option2;
    @Column(nullable = false)
    private String option3;
    @Column(nullable = false)
    private String option4;
    @Column(nullable = false)
    private String correctAnswer;  // Correct answer option text

//show many question have one set
    @ManyToOne
    @JoinColumn(name = "set_exam_id")
    @JsonIgnore
    private ModelSetExam modelSetExam;
}
