package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private HBox buttonsContainer;

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

        question.getSelectedAnswers().clear();

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
        initButtons();

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

    private void initButtons(){
        var btnNext = new Button();
        btnNext.setText("next");
        btnNext.setOnAction(e -> onNextButtonClick());

        if(questionNumber == 1) {
            buttonsContainer.getChildren().add(btnNext);
        }
        else{
            var btnPrev = new Button();
            btnPrev.setText("previous");
            btnPrev.setOnAction(e -> onPreviousButtonClick());

            buttonsContainer.getChildren().add(btnPrev);

            if(questionNumber == numberOfQuestions){
                var btnFinish = new Button();
                btnFinish.setText("finish");
                btnFinish.setOnAction(e -> onFinishButtonClick());

                buttonsContainer.getChildren().add(btnFinish);
            }
            else{
                buttonsContainer.getChildren().add(btnNext);
            }
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
