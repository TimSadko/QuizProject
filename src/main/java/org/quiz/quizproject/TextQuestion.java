package org.quiz.quizproject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TextQuestion extends Question {
    private final StringProperty txt = new SimpleStringProperty();

    private String text;

    @JsonIgnore
    private final QuestionType type = QuestionType.Text;

    private int points;

    private String[] correctAnswers;
    private String enteredAnswer;
    private boolean isAnswerSelected = false;

    public TextQuestion(){

    }

    @Override
    public StringProperty textProperty(){
        return txt;
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

    @Override
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
        txt.set(text);
        this.text = text;
    }

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public int calcPoints() {
        if(!isAnswerSelected) return 0;
        return Arrays.asList(correctAnswers).contains(cleanString(enteredAnswer)) ? points : 0;
    }

    private String cleanString(String input){
        return input.toLowerCase().replaceAll("\\s+",""); // Remove
    }
}
