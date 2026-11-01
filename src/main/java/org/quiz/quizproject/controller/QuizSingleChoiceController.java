package org.quiz.quizproject.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.quiz.quizproject.models.SingleChoiceQuestion;

import java.util.Objects;

public class QuizSingleChoiceController extends BaseQuestionController implements QuizController {
    @FXML
    private ToggleGroup scGroup;

    @FXML
    private VBox vbox;

    @Override
    protected void saveAnswer(){
        var rb = (RadioButton)scGroup.getSelectedToggle();

        if(rb == null){
            getQuestion().setIsAnswerSelected(false);
        }
        else{
            getQuestion().setIsAnswerSelected(true);
            getQuestion().setSelectedAnswer(rb.getText());
        }
    }

    @Override
    protected boolean isAnswerSelected() {
        var rb = (RadioButton)scGroup.getSelectedToggle();

        return rb != null;
    }

    @Override
    public void init(){
        RadioButton[] radioButtons = new RadioButton[getQuestion().getChoices().length];

        for(int i = 0; i < radioButtons.length; i++){
            radioButtons[i] = new RadioButton();
            radioButtons[i].setText(getQuestion().getChoices()[i]);
            radioButtons[i].setToggleGroup(scGroup);

            if(question.getIsAnswerSelected() && Objects.equals(radioButtons[i].getText(), getQuestion().getSelectedAnswer())) radioButtons[i].setSelected(true);

            if(i == 0) VBox.setMargin(radioButtons[i], new Insets(4, 0, 0, 0));
            else VBox.setMargin(radioButtons[i], new Insets(2, 0, 0, 0));

            vbox.getChildren().add(radioButtons[i]);
        }

        BaseInit();
    }

    public SingleChoiceQuestion getQuestion() {
        return (SingleChoiceQuestion) question;
    }
}
