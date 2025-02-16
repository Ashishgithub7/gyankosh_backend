package com.example.gyankosh.controller;
import com.example.gyankosh.Entity.Question;
import com.example.gyankosh.dto.QuestionResponseDto;
import com.example.gyankosh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuestionController {

    private QuestionService questionService;
    private String finalFeedback="";

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Step 1: Start quiz by selecting course and user ID
    @PostMapping("/start")
    public Question startQuiz(@RequestParam String course,@RequestParam Long userId)
    {
        // Fetch the first adaptive question with medium difficulty
        return questionService.getAdaptiveQuestion("medium",course,userId);

    }

    // Step 2: Submit answer, process quiz state, and provide the next question
    @PostMapping("/submit")
    public QuestionResponseDto submitAnswer(@RequestParam String answer, @RequestParam Long userId)
    {
        // Fetch current question
        Question currentQuestion=questionService.getUserCurrentQuestion(userId);

        // Check if the provided answer is correct
        boolean isCorrect=answer.equals(currentQuestion.getCorrectAnswer());

        // Process the answer
        questionService.processAnswer(currentQuestion,isCorrect,userId);

        // Check the quiz is complete or not
       boolean isQuizComplete=questionService.isQuizComplete(questionService.getUserCurrentDifficulty(userId),currentQuestion.getCourse(),userId);
        if (isQuizComplete)
        {
            String formattedNumber = String.format("%.3f", questionService.calculateAbilityMeasure(userId)); // Format to 2 decimal places
            finalFeedback="Your Ability is:"+formattedNumber+" "+"Quiz Finished.. Your score:"+ questionService.getScore(userId)+"."+questionService.provideFinalFeedback(userId);
            questionService.resetQuiz(userId);  // Reset quiz after completion
        }

        // Update the difficulty based on the answer
        questionService.updateDifficulty(isCorrect,userId);

        // Fetch the next question based on the updated difficulty
        String nextDifficulty=questionService.getUserCurrentDifficulty(userId);
        Question nextQuestion=questionService.getAdaptiveQuestion(nextDifficulty,currentQuestion.getCourse(),userId);
        return new QuestionResponseDto(
                nextQuestion.getQuestionText(),
                nextDifficulty,
                nextQuestion.getCorrectAnswer(),
                nextQuestion.getOptionA(),
                nextQuestion.getOptionB(),
                nextQuestion.getOptionC(),
                nextQuestion.getOptionD(),
                nextQuestion.getFeedback(),
                finalFeedback,
                isQuizComplete

        );

    }

    // Step 3: Get current score for the user
    @GetMapping("/score")
    public int getCurrentScore(@RequestParam Long userId) {
        return questionService.getScore(userId);
    }


//    @GetMapping("/try")
//    public String tryyourself()
//    {
//        return "welcome to controller";
//    }

}
