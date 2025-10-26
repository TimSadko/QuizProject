package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class QuizTrueFalseController {
    private boolean mustSelect;
    private int numberOfQuestions;
    private int questionNumber;

    private TrueFalseQuestion question;

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
    private RadioButton radioTrue;

    @FXML
    private RadioButton radioFalse;

    @FXML
    private ToggleGroup tfGroup;

    public void onPreviousButtonClick(){
        saveAnswer();

        QuizApplication.getInstance().moveToQuestion(questionNumber - 1);
    }

    public void onNextButtonClick(){
        saveAnswer();

        QuizApplication.getInstance().moveToQuestion(questionNumber + 1);
    }

    public void onFinishButtonClick(){
        saveAnswer();

        QuizApplication.getInstance().finishQuiz();
    }

    private void saveAnswer(){
        var rb = (RadioButton)tfGroup.getSelectedToggle();

        question.setIsAnswerSelected(rb.isSelected());

        if(mustSelect && !rb.isSelected()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("An answer must be selected!");

            alert.showAndWait();
        }

        if(rb == radioTrue) question.setSelectedAnswer(true);
        else if(rb == radioFalse) question.setSelectedAnswer(false);
    }

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
        questionTextLabel.setText(question.getText());
    }

    public boolean isMustSelect() {
        return mustSelect;
    }

    public void setMustSelect(boolean mustSelect) {
        this.mustSelect = mustSelect;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public TrueFalseQuestion getQuestion() {
        return question;
    }

    public void setQuestion(TrueFalseQuestion question) {
        this.question = question;
    }
}
