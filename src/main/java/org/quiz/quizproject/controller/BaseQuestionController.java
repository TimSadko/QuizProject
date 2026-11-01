package org.quiz.quizproject.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.quiz.quizproject.GameManager;
import org.quiz.quizproject.QuizApplication;
import org.quiz.quizproject.models.AttemptResult;
import org.quiz.quizproject.models.Question;
import org.quiz.quizproject.models.QuestionWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BaseQuestionController {
    protected int numberOfQuestions;
    protected int questionNumber;

    protected Timeline timer;
    protected int timeLeft;

    protected int timeLimit;
    protected Question question;

    @FXML
    protected Label timeLeftLabel;

    @FXML
    protected Label questionNumberLabel;

    @FXML
    protected Label questionTextLabel;

    @FXML
    protected Button actionButton;

    @FXML
    protected ProgressBar timeProgressBar;

    private SimpleStringProperty propertyTimeLeft = new SimpleStringProperty();
    private DoubleProperty propertyTimeLeftPercent = new SimpleDoubleProperty();

    protected void showNotAnsweredAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("An answer must be selected!");

        alert.show();
    }

    protected void saveAnswer(){}

    protected boolean isAnswerSelected() { return false; }

    public void onNextButtonClick(){
        if(question.isRequired() && !isAnswerSelected()) {
            showNotAnsweredAlert();
            return;
        }

        if(timer != null) timer.stop();

        moveToNextQuestion();
    }

    public void onFinishButtonClick(){
        if(question.isRequired() && !isAnswerSelected()) {
            showNotAnsweredAlert();
            return;
        }

        if(timer != null) timer.stop();

        finishQuiz();
    }

    protected void moveToNextQuestion(){
        saveAnswer();
        GameManager.getInstance().moveToQuestion(questionNumber + 1);
    }

    protected void finishQuiz(){
        saveAnswer();
        GameManager.getInstance().finishQuiz();
    }

    protected void timerRunOutTime(){
        if(questionNumber == numberOfQuestions) finishQuiz();
        else moveToNextQuestion();
    }

    protected void startTimer()
    {
        timeLeftLabel.setText(timeLimit + " s");

        timeLeft = timeLimit;

        timer = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    setTimeLeft(timeLeft - 1);

                    if (timeLeft == 0)
                    {
                        timer.stop();
                        timerRunOutTime();
                    }
                }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    protected void BaseInit(){
        // Init buttons
        if(questionNumber == numberOfQuestions){
            actionButton.setText("finish");
            actionButton.setOnAction(e -> onFinishButtonClick());
        }
        else{
            actionButton.setText("next");
            actionButton.setOnAction(e -> onNextButtonClick());
        }

        // Init question number label
        questionNumberLabel.setText(questionNumber + "/" + numberOfQuestions);
        questionTextLabel.setText(question.getTitle());

        // Init timer label and progress bar
        timeLeftLabel.textProperty().bindBidirectional(propertyTimeLeft);
        timeProgressBar.progressProperty().bindBidirectional(propertyTimeLeftPercent);

        // Start timer, if needed
        timeProgressBar.setProgress(1);

        if(timeLimit <= 0) setTimeLeft(0);
        else{
            startTimer();
        }
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setQuestion(QuestionWrapper q) {
        this.question = q.getQuestion();
        this.timeLimit = q.GetTimeLimit();
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;

        if(timeLeft < 0){
            propertyTimeLeft.set("Unlimited");
            propertyTimeLeftPercent.set(1);
        }
        else{
            propertyTimeLeft.set(timeLeft + "s");
            propertyTimeLeftPercent.set((double) timeLeft / timeLimit);
        }
    }
}
