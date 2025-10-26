package org.quiz.quizproject;

public interface QuizController {
    public void setQuestion(Question question);
    public void setMustSelect(boolean mustSelect);
    public void setNumberOfQuestions(int numberOfQuestions);
    public void setQuestionNumber(int questionNumber);
    public void init();
}
