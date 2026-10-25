package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class QuizTextController implements QuizController {
    private int numberOfQuestions;
    private int questionNumber;

    private TextQuestion question;

    private int timeLimit;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Label questionTextLabel;

    @FXML
    private HBox buttonsContainer;

    @FXML
    private TextField textField;

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
        if(textField.getText().trim().length() <= 1 && question.isRequired() && !noMessage){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("An answer must be selected!");

            alert.show();

            return false;
        }

        question.setIsAnswerSelected(true);
        question.setEnteredAnswer(textField.getText());

        return true;
    }

    @Override
    public void init(){
        initButtons();

        questionNumberLabel.setText(questionNumber + "/" + numberOfQuestions);
        questionTextLabel.setText(question.getTitle());

        if(question.getIsAnswerSelected()) textField.setText(question.getEnteredAnswer());
    }

    private void initButtons(){
        if(questionNumber == numberOfQuestions){
            var btnFinish = new Button();
            btnFinish.setText("finish");
            btnFinish.setOnAction(e -> onFinishButtonClick());

            buttonsContainer.getChildren().add(btnFinish);
        }
        else{
            var btnNext = new Button();
            btnNext.setText("next");
            btnNext.setOnAction(e -> onNextButtonClick());

            buttonsContainer.getChildren().add(btnNext);
        }
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

    public TextQuestion getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(QuestionWrapper q) {
        this.question = (TextQuestion) q.getQuestion();
        this.timeLimit = q.GetTimeLimit();
    }
}
