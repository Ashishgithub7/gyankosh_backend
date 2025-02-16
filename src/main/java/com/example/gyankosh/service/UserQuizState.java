package com.example.gyankosh.service;
import com.example.gyankosh.Entity.Question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserQuizState {

    public  int questionCount=0; // Number of questions answered
    public int correctAnswer=0;  // Number of correct answers
    public int score=0;   // Total score
    public List<Double> scoreList=new ArrayList<>(); //scorelist rakhana for find mean,variance
    public boolean lastAnswerCorrect=true; //suruma correct rakhana
    public Set<Long> askedQuestionIds = new HashSet<>(); // Track asked question IDs
    public double totalDifficulty = 0.0; // H: Sum of difficulties vanu ra score i.e medium ,easy ra hard ko score xut xutai jodnu

    //user-specific states
    public String currentDifficulty="medium";
    public Question currentQuestion;  //stores the current question for the user

}

