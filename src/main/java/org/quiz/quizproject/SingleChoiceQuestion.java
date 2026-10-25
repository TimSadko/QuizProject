package org.quiz.quizproject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleChoiceQuestion extends Question {
    private final StringProperty txt = new SimpleStringProperty();

    private String title;
    private String name;

    @JsonIgnore
    private final QuestionType type = QuestionType.SingleChoice;

    //private int points;

    private String choicesOrder = "";

    private String[] choices;

    private String correctAnswer;
    private String selectedAnswer;
    private boolean isAnswerSelected = false;

    private boolean isRequired = true;

    public SingleChoiceQuestion(){

    }

    @Override
    public int calPoints(){
        if(isAnswerSelected){
            return correctAnswer.equals(selectedAnswer) ? 1 : 0;
        }

        return 0;
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

    @Override
    public int getPoints() {
        return 1;
    }

    public void setPoints(int points) {
        //this.points = points;
    }

    @Override
    public QuestionType getType() {
        return type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getShuffleChoices(){
        return choicesOrder.equals("random");
    }

    public String getChoicesOrder() {
        return choicesOrder;
    }

    public void setChoicesOrder(String choicesOrder) {
        this.choicesOrder = choicesOrder;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    @Override
    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }
}
