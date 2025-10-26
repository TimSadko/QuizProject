package org.quiz.quizproject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleChoiceQuestion extends Question {
    private final StringProperty txt = new SimpleStringProperty();

    private String text;

    @JsonIgnore
    private final QuestionType type = QuestionType.SingleChoice;

    private int points;

    private String[] answers;

    private String correctAnswer;
    private String selectedAnswer;
    private boolean isAnswerSelected = false;

    public SingleChoiceQuestion(){

    }

    @Override
    public StringProperty textProperty(){
        return txt;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    @Override
    public boolean getIsAnswerSelected() {
        return isAnswerSelected;
    }

    public void setIsAnswerSelected(boolean isAnswerSelected) {
        this.isAnswerSelected = isAnswerSelected;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
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
        txt.set(text);
        this.text = text;
    }

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public int calcPoints() {
        return isAnswerSelected ? Objects.equals(selectedAnswer, correctAnswer) ? points : 0 : 0;
    }
}
