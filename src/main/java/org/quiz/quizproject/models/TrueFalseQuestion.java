package org.quiz.quizproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrueFalseQuestion extends Question {
    private final StringProperty txt = new SimpleStringProperty();

    private String title;
    private String name;

    private String labelTrue;
    private String labelFalse;

    @JsonIgnore
    private final QuestionType type = QuestionType.TrueFalse;

    private boolean correctAnswer;
    private boolean selectedAnswer;
    private boolean isAnswerSelected = false;
    private boolean isRequired = true;

    public TrueFalseQuestion(){

    }

    @Override
    public boolean isAnswerCorrect(){
        if(isAnswerSelected){
            return correctAnswer == selectedAnswer;
        }

        return false;
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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        txt.set(title);
        this.title = title;
    }

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelTrue() {
        return labelTrue;
    }

    public void setLabelTrue(String labelTrue) {
        this.labelTrue = labelTrue;
    }

    public String getLabelFalse() {
        return labelFalse;
    }

    public void setLabelFalse(String labelFalse) {
        this.labelFalse = labelFalse;
    }

    @Override
    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

//    @Override
//    public int getPoints() {
//        return points;
//    }
//
//    public void setPoints(int points) {
//        this.points = points;
//    }
}
