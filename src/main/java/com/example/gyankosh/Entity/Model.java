package com.example.gyankosh.Entity;

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
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qid;
    @Column(nullable = false)
    private String course; // e.g., IOE, IOM, Loksewa, +2
    @Column(nullable = false)
    private String setName;// e.g., Set 1, Set 2
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
    private String correctAnswer;
}
