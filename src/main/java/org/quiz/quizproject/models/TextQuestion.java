package org.quiz.quizproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TextQuestion extends Question {
    private final StringProperty txt = new SimpleStringProperty();

    private String title;
    private String name;

    @JsonIgnore
    private final QuestionType type = QuestionType.Text;

    //private int points;

    private String[] correctAnswers;
    private String enteredAnswer;
    private boolean isAnswerSelected = false;

    private boolean isRequired = true;

    public TextQuestion(){

    }

    @Override
    public boolean isAnswerCorrect(){
        return false;
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
    public QuestionType getType() {
        return type;
    }

    private String cleanString(String input){
        return input.toLowerCase().replaceAll("\\s+",""); // Remove
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

    @Override
    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }
}
