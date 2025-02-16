package com.example.gyankosh.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class ModelSetExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long course_id;
    @Column(nullable = false)
    private String examType; // e.g., IOE, IOM, Loksewa, +2
    @Column(nullable = false)
    private String setName;// e.g., Set 1, Set 2
    @OneToMany(mappedBy = "modelSetExam",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ModelSetQuestions> modelSetQuestionsList;
}
