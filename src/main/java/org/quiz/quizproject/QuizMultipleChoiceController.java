package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class QuizMultipleChoiceController implements QuizController {
    private boolean mustSelect;
    private int numberOfQuestions;
    private int questionNumber;

    private MultipleChoiceQuestion question;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Label questionTextLabel;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button finishButton;

    @FXML
    private VBox vbox;

    private RadioButton[] radioButtons;

    public void onPreviousButtonClick(){
        saveAnswer(true);

        QuizApplication.getInstance().moveToQuestion(questionNumber - 1);
    }

    public void onNextButtonClick(){
        if(!saveAnswer(false)) return;

        QuizApplication.getInstance().moveToQuestion(questionNumber + 1);
    }

    public void onFinishButtonClick(){
        if(!saveAnswer(false)) return;

        QuizApplication.getInstance().finishQuiz();
    }

    private boolean saveAnswer(boolean noMessage){
        boolean atLeastOneSelected = false;

        for (int i = 0; i < radioButtons.length; i++){
            if(radioButtons[i].isSelected()){
                atLeastOneSelected = true;
                question.getSelectedAnswers().add(radioButtons[i].getText());
            }
        }

        question.setIsAnswerSelected(atLeastOneSelected);

        if(!atLeastOneSelected && mustSelect && !noMessage){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("An answer must be selected!");

            alert.show();

            return false;
        }

        return true;
    }

    @Override
    public void init(){
        if(questionNumber == 1) {
            prevButton.setVisible(false);
            finishButton.setVisible(false);
        }
        else{
            if(questionNumber == numberOfQuestions){
                nextButton.setVisible(false);
            }
            else{
                finishButton.setVisible(false);
            }
        }

        questionNumberLabel.setText(questionNumber + "/" + numberOfQuestions);
        questionTextLabel.setText(question.getText() + " (Multiple answers may be chosen)");

        radioButtons = new RadioButton[question.getAnswers().length];

        for(int i = 0; i < radioButtons.length; i++){
            radioButtons[i] = new RadioButton();
            radioButtons[i].setText(question.getAnswers()[i]);

            if(question.getIsAnswerSelected() && question.getSelectedAnswers().contains(radioButtons[i].getText())) radioButtons[i].setSelected(true);

            vbox.getChildren().add(radioButtons[i]);
        }
    }

    public boolean isMustSelect() {
        return mustSelect;
    }

    @Override
    public void setMustSelect(boolean mustSelect) {
        this.mustSelect = mustSelect;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    @Override
    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public MultipleChoiceQuestion getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question question) {
        this.question = (MultipleChoiceQuestion)question;
    }
}
