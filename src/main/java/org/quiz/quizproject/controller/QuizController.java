package org.quiz.quizproject.controller;

import org.quiz.quizproject.models.QuestionWrapper;

public interface QuizController {
    public void setQuestion(QuestionWrapper question);
    public void setNumberOfQuestions(int numberOfQuestions);
    public void setQuestionNumber(int questionNumber);
    public void init();
}
