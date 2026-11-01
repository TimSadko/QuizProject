package org.quiz.quizproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.quiz.quizproject.models.TrueFalseQuestion;

public class QuizTrueFalseController extends BaseQuestionController implements QuizController {
    @FXML
    private RadioButton radioTrue;

    @FXML
    private RadioButton radioFalse;

    @FXML
    private ToggleGroup tfGroup;

    @Override
    protected void saveAnswer(){
        var rb = (RadioButton)tfGroup.getSelectedToggle();

        if(rb == null){
            getQuestion().setIsAnswerSelected(false);
        }
        else{
            getQuestion().setIsAnswerSelected(true);

            getQuestion().setSelectedAnswer(rb == radioTrue);
        }
    }

    @Override
    protected boolean isAnswerSelected() {
        var rb = (RadioButton)tfGroup.getSelectedToggle();

        return rb != null;
    }

    @Override
    public void init(){
        // Add radio group
        radioTrue.setText(getQuestion().getLabelTrue());
        radioFalse.setText(getQuestion().getLabelFalse());

        radioFalse.setToggleGroup(tfGroup);
        radioTrue.setToggleGroup(tfGroup);

        // Set selection
        if(question.getIsAnswerSelected()){
            if(getQuestion().getSelectedAnswer()) radioTrue.setSelected(true);
            else radioFalse.setSelected(true);
        }

        BaseInit();
    }

    public TrueFalseQuestion getQuestion() {
        return (TrueFalseQuestion) question;
    }
}
