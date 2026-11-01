package org.quiz.quizproject.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import org.quiz.quizproject.models.MultipleChoiceQuestion;

public class QuizMultipleChoiceController extends BaseQuestionController implements QuizController {
    @FXML
    private VBox vbox;

    private RadioButton[] radioButtons;

    @Override
    protected void saveAnswer(){
        if(isAnswerSelected()){
            getQuestion().getSelectedAnswers().clear();

            for (RadioButton radioButton : radioButtons) {
                if (radioButton.isSelected()) {
                    getQuestion().getSelectedAnswers().add(radioButton.getText());
                }
            }

            getQuestion().setIsAnswerSelected(true);
        }
        else {
            getQuestion().setIsAnswerSelected(false);
        }
    }

    @Override
    protected boolean isAnswerSelected() {
        boolean atLeastOneSelected = false;

        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isSelected()) {
                atLeastOneSelected = true;
                break;
            }
        }

        return atLeastOneSelected;
    }

    @Override
    public void init(){
        radioButtons = new RadioButton[getQuestion().getChoices().length];

        for(int i = 0; i < radioButtons.length; i++){
            radioButtons[i] = new RadioButton();
            radioButtons[i].setText(getQuestion().getChoices()[i]);

            if(question.getIsAnswerSelected() && getQuestion().getSelectedAnswers().contains(radioButtons[i].getText())) radioButtons[i].setSelected(true);

            if(i == 0) VBox.setMargin(radioButtons[i], new Insets(4, 0, 0, 0));
            else VBox.setMargin(radioButtons[i], new Insets(2, 0, 0, 0));

            vbox.getChildren().add(radioButtons[i]);
        }

        BaseInit();
    }

    public MultipleChoiceQuestion getQuestion() {
        return (MultipleChoiceQuestion) question;
    }
}
