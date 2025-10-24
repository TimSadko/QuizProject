package org.quiz.quizproject;

public abstract class Question {
    public abstract boolean getIsAnswerSelected();
    public abstract String getText();
    public abstract QuestionType getType();
    public abstract int calcPoints();
}
