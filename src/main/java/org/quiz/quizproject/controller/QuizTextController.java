package org.quiz.quizproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.quiz.quizproject.models.TextQuestion;

public class QuizTextController extends BaseQuestionController implements QuizController {
    @FXML
    private TextField textField;

    @Override
    protected void saveAnswer(){
        if(isAnswerSelected()){
            getQuestion().setEnteredAnswer(textField.getText());
            getQuestion().setIsAnswerSelected(true);
        }
        else{
            getQuestion().setIsAnswerSelected(false);
        }
    }

    @Override
    protected boolean isAnswerSelected() {
        return textField.getText().trim().length() <= 1;
    }

    @Override
    public void init(){
        if(question.getIsAnswerSelected()) textField.setText(getQuestion().getEnteredAnswer());

        BaseInit();
    }

    public TextQuestion getQuestion() {
        return (TextQuestion) question;
    }
}
