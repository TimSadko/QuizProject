package org.quiz.quizproject;

import java.util.Arrays;

public class TextQuestion extends Question {

    private String text;
    private final QuestionType type = QuestionType.TrueFalse;
    private int points;

    private String[] correctAnswers;
    private String enteredAnswer;
    private boolean isAnswerSelected;

    public TextQuestion(){

    }

    @Override
    public boolean getIsAnswerSelected() {
        return isAnswerSelected;
    }

    public void setIsAnswerSelected(boolean isAnswerSelected) {
        this.isAnswerSelected = isAnswerSelected;
    }

    public String getEnteredAnswer() {
        return enteredAnswer;
    }

    public void setEnteredAnswer(String enteredAnswer) {
        this.enteredAnswer = enteredAnswer;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String[] correctAnswers) {
        for (int i = 0; i < correctAnswers.length; i++) {
            correctAnswers[i] = cleanString(correctAnswers[i]); // To lower case and remove all invisible characters (space, tab, etc.) from correct answers
        }

        this.correctAnswers = correctAnswers;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public int calcPoints() {
        return Arrays.asList(correctAnswers).contains(cleanString(enteredAnswer)) ? points : 0;
    }

    private String cleanString(String input){
        return input.toLowerCase().replaceAll("\\s+",""); // Remove
    }
}
