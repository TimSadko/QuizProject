package org.quiz.quizproject;

public interface QuizController {
    public void setQuestion(QuestionWrapper question);
    public void setNumberOfQuestions(int numberOfQuestions);
    public void setQuestionNumber(int questionNumber);
    public void init();
}
