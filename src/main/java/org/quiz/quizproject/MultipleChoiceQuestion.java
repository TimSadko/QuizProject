package org.quiz.quizproject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MultipleChoiceQuestion extends Question {
    private final StringProperty txt = new SimpleStringProperty();

    private String text;

    @JsonIgnore
    private final QuestionType type = QuestionType.MultipleChoice;

    private int points;

    private String[] answers;

    private String[] correctAnswers;
    private ArrayList<String> selectedAnswers = new ArrayList<String>();
    private boolean isAnswerSelected = false;

    public MultipleChoiceQuestion(){

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
        if(correctAnswers.length == selectedAnswers.size() && selectedAnswers.containsAll(Arrays.asList(correctAnswers))) return points;
        else return 0;
    }
}
