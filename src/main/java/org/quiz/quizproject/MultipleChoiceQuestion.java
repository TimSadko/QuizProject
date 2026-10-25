package org.quiz.quizproject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MultipleChoiceQuestion extends Question {
    private final StringProperty txt = new SimpleStringProperty();

    private String title;
    private String name;

    @JsonIgnore
    private final QuestionType type = QuestionType.MultipleChoice;

    //private int points;

    private String[] choices;

    private String choicesOrder = "";

    private String[] correctAnswers;
    private ArrayList<String> selectedAnswers = new ArrayList<String>();
    private boolean isAnswerSelected = false;

    private boolean isRequired  = true;

    public MultipleChoiceQuestion(){

    }

    @Override
    public int calPoints(){
        if(isAnswerSelected){
            if(correctAnswers.length != selectedAnswers.size()) return 0;

            return correctAnswers.length == selectedAnswers.size() && selectedAnswers.containsAll(Arrays.asList(correctAnswers)) ? 1 : 0;
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

    public ArrayList<String> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(ArrayList<String> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String[] correctAnswers) {
        this.correctAnswers = correctAnswers;
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

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getChoicesOrder() {
        return choicesOrder;
    }

    public void setChoicesOrder(String choicesOrder) {
        this.choicesOrder = choicesOrder;
    }

    @Override
    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }
}
