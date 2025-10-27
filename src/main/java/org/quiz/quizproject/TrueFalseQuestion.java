package org.quiz.quizproject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrueFalseQuestion extends Question {
    private final StringProperty txt = new SimpleStringProperty();

    private String text;

    @JsonIgnore
    private final QuestionType type = QuestionType.TrueFalse;
    private int points;

    private boolean correctAnswer;
    private boolean selectedAnswer;
    private boolean isAnswerSelected = false;

    public TrueFalseQuestion(){

    }

    @Override
    public boolean getIsAnswerSelected() {
        return isAnswerSelected;
    }

    @Override
    public StringProperty textProperty(){
        return txt;
    }

    public void setIsAnswerSelected(boolean isAnswerSelected) {
        this.isAnswerSelected = isAnswerSelected;
    }

    public boolean getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(boolean selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
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
        return isAnswerSelected ? selectedAnswer == correctAnswer ? points : 0 : 0;
    }
}
