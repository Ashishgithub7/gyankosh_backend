package com.example.gyankosh.service;

import com.example.gyankosh.Entity.Question;
import com.example.gyankosh.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    private final double STANDARD_ERROR_THRESHOLD = 0.03;  //0.0702 rakhna later for check 0.5
    private final int MAX_QUESTIONS = 50;

    //A map to store each user's quiz state
    private Map<Long,UserQuizState> userQuizStateMap=new HashMap<>();

    // Step 1: Get or create a user's quiz state
    private UserQuizState getUserState(Long userId) {
        return userQuizStateMap.computeIfAbsent(userId, k -> new UserQuizState());
    }

    // Step 2: Fetch adaptive question based on difficulty and course
   public Question getAdaptiveQuestion(String difficulty, String course,Long userId)
    {
        UserQuizState userState=getUserState(userId);//Get or Create User State
        List<Question> questions= questionRepository.findByDifficultyAndCourse(difficulty,course);

        // Filter out already asked questions
        questions.removeIf(q ->userState.askedQuestionIds.contains(q.getId()));
        Collections.shuffle(questions);// Randomize the order of available questions

        // Return the first question if available
        if(!questions.isEmpty())
        {
            userState.currentQuestion=questions.get(0); //store the currentquestion for this user
        }
        return userState.currentQuestion;
    }


    // Step 3: Determine next difficulty based on correctness of previous answer
    public String determineNextDifficulty(String currentDifficulty,boolean isCorrect)
    {
        if(isCorrect)
        {
            if (currentDifficulty.equals("easy")) return "medium";
            if(currentDifficulty.equals("medium")) return "hard";
            return "hard";
        }
        else
        {
            if (currentDifficulty.equals("hard")) return "medium";
            else if(currentDifficulty.equals("medium")) return "easy";
            return "easy";
        }
    }

    // Step 4: Update user's difficulty level
    public void updateDifficulty(boolean isCorrect,Long userId)
    {
        UserQuizState userState = getUserState(userId);
        userState.currentDifficulty=determineNextDifficulty(userState.currentDifficulty,isCorrect);
    }

    // Step 5: Update score based on correctness and difficulty level
    public void updateScore(boolean isCorrect, String difficulty,Long userId)
    {
        UserQuizState userState = getUserState(userId);
        if (isCorrect)
        {
            userState.score+=difficulty.equals("easy")?1:difficulty.equals("medium")?2 : 3;
        }
    }

    // Step 6: Add the current score to the score list for standard error calculation
    public void updateScoreList(Long userId)
    {
     UserQuizState userState = getUserState(userId);
     //kati ota answer correct garyo/katiota question deyo
     double currentScore=(double) userState.correctAnswer/userState.questionCount;
     userState.scoreList.add(currentScore);
    }

    // Step 7: Calculate the standard error from the score list
    public double calculateStandardError(Long userId)
    {
        UserQuizState userState = getUserState(userId);
        double meanScore=userState.scoreList.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        //test code
        System.out.println(meanScore);
        double variance = userState.scoreList.stream().mapToDouble(score -> Math.pow(score - meanScore, 2)).average().orElse(0.0);
        //test code
        System.out.println(variance);
        return Math.sqrt(variance)/Math.sqrt(userState.questionCount);
    }

    // Step 8: Check if easier questions are unavailable
    public boolean noEasierQuestionsAvailable(String currentDifficulty,String course,Long userId)
    {
        String nextDifficulty=determineNextDifficulty(currentDifficulty,false); //false rakhunako reason medium ma vayera bigrda easy ma gainxa
        List<Question> questions=questionRepository.findByDifficultyAndCourse(nextDifficulty,course);
        return questions.isEmpty(); //The method returns true if the questions list is empty (i.e., no easier questions are available).

    }
    // Step 9: Check if harder questions are unavailable
    public boolean noHarderQuestionsAvailable(String currentDifficulty, String course,Long userId) {
        String nextDifficulty = determineNextDifficulty(currentDifficulty, true);//true rakhunako reason medium ma vayera melauda hard ma gainxa
        List<Question> questions = questionRepository.findByDifficultyAndCourse(nextDifficulty, course);
        return questions.isEmpty();  //The method returns true if the questions list is empty (i.e., no harder questions are available).
    }


//    // Step 10: Provide final feedback after the quiz is complete
//    public String provideInstantFeedback(Question question,String userAnswer) {
//        if (userAnswer.equals(question.getCorrectAnswer())) {
//            return "Correct!" + question.getFeedback();
//        } else {
//            return "Incorrect" + question.getFeedback();
//        }
//    }

    // Step 10: Provide final feedback after the quiz is complete
        public String provideFinalFeedback(Long userId)
        {
            UserQuizState userState = getUserState(userId);
            if (userState.score >= 30) return "Excellent performance! You've mastered the material.";
            if (userState.score >= 20) return "Good job! But there's room for improvement.";
            return "Needs improvement. Review the basics and try again.";
        }

    // Step 11: Process the submitted answer and update the quiz state
    public void processAnswer(Question currentQuestion, boolean isCorrect,Long userId) {
        UserQuizState userState = getUserState(userId);
        userState.questionCount++;
        userState.askedQuestionIds.add(currentQuestion.getId()); // Add the question ID to the set
        userState.totalDifficulty=userState.totalDifficulty+ getDifficultyValue(currentQuestion.getDifficulty()); // Update total difficulty

        if (isCorrect) {
            userState.correctAnswer++;
        }

        // Update score and score list
        updateScore(isCorrect,currentQuestion.getDifficulty(),userId);
        updateScoreList(userId);
        //add grna ki nai paxi sochna

    }
    // Step 12: Check if the quiz is complete
    public boolean isQuizComplete(String currentDifficulty,String course,Long userId)
    {
        UserQuizState userState = getUserState(userId);
        double standardError=calculateStandardError(userId);

     // Log the standard error and other metrics
        System.out.println("Standard Error: " + standardError);
        System.out.println("Questions Answered: " + userState.questionCount);
        System.out.println("Correct Answers: " + userState.correctAnswer);
    // Check if no more questions are available
        boolean noEasier = noEasierQuestionsAvailable(currentDifficulty,course,userId);
        boolean noHarder = noHarderQuestionsAvailable(currentDifficulty,course,userId);
        System.out.println("No Easier Questions Available: " + noEasier);
        System.out.println("No Harder Questions Available: " + noHarder);

        return (userState.questionCount >= 5 && standardError <= STANDARD_ERROR_THRESHOLD )||
                (!userState.lastAnswerCorrect && noEasierQuestionsAvailable(currentDifficulty, course,userId)) ||
                (userState.lastAnswerCorrect && noHarderQuestionsAvailable(currentDifficulty, course,userId)) ||
                userState.questionCount >= MAX_QUESTIONS;

    }
    // Step 13: Calculate ability measure based on the user's performance
    public double calculateAbilityMeasure(Long userId) {
        UserQuizState userState = getUserState(userId);
        double averageDifficulty = userState.totalDifficulty / userState.questionCount; // H / L
        double incorrectAnswers = userState.questionCount - userState.correctAnswer; // W
        double correctIncorrectRatio = userState.correctAnswer / Math.max(incorrectAnswers, 1); // R / W, avoid division by zero
        return averageDifficulty + Math.log(correctIncorrectRatio);
    }

    // Step 14: Utility to map difficulty to numerical value
    private double getDifficultyValue(String difficulty) {
        switch (difficulty) {
            case "easy": return 1.0;
            case "medium": return 2.0;
            case "hard": return 3.0;
            default: return 0.0;
        }
    }

    // Step 15: Fetch the current question being asked to the user
    public Question getUserCurrentQuestion(Long userId)
    {
        return getUserState(userId).currentQuestion;
    }

    // Step 16: Fetch the current difficulty level for the user
    public String getUserCurrentDifficulty(Long userId)
    {
        return getUserState(userId).currentDifficulty;
    }

    // Step 17: Reset quiz state for a new quiz session
    public void resetQuiz(Long userId) {
        userQuizStateMap.remove(userId);
    }

    // Step 18: Fetch user's score
    public int getScore(Long userId)
    {
        return  getUserState(userId).score;
    }

}


//Standard Error Threshold:
//
//If the calculated standard error is less than or equal to the STANDARD_ERROR_THRESHOLD, it means that the user's performance is consistent, and the quiz can end.
//No Easier Questions Available:
//
//If the last answer was incorrect and there are no easier questions available, the quiz should stop because the system can't find easier questions to adjust the difficulty downward.
//No Harder Questions Available:
//
//If the last answer was correct and there are no harder questions available, the quiz should stop because the system can't find harder questions to adjust the difficulty upward.
//Maximum Question Limit:
//
//If the number of questions asked (questionCount) reaches the maximum allowed (MAX_QUESTIONS), the quiz should end.