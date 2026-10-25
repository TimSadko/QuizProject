package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class QuizSingleChoiceController implements QuizController {
    private int numberOfQuestions;
    private int questionNumber;

    private SingleChoiceQuestion question;

    private int timeLimit;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Label questionTextLabel;

    @FXML
    private HBox buttonsContainer;

    @FXML
    private ToggleGroup scGroup;

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
        var rb = (RadioButton)scGroup.getSelectedToggle();

        if(rb == null){
            if(question.isRequired() && !noMessage){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("An answer must be selected!");

                alert.show();

                return false;
            }

            return true;
        }

        question.setIsAnswerSelected(rb.isSelected());
        question.setSelectedAnswer(rb.getText());

        return true;
    }

    @Override
    public void init(){
        initButtons();

        questionNumberLabel.setText(questionNumber + "/" + numberOfQuestions);
        questionTextLabel.setText(question.getTitle());

        radioButtons = new RadioButton[question.getChoices().length];

        for(int i = 0; i < radioButtons.length; i++){
            radioButtons[i] = new RadioButton();
            radioButtons[i].setText(question.getChoices()[i]);
            radioButtons[i].setToggleGroup(scGroup);

            if(question.getIsAnswerSelected() && Objects.equals(radioButtons[i].getText(), question.getSelectedAnswer())) radioButtons[i].setSelected(true);

            if(i == 0) VBox.setMargin(radioButtons[i], new Insets(4, 0, 0, 0));
            else VBox.setMargin(radioButtons[i], new Insets(2, 0, 0, 0));

            vbox.getChildren().add(radioButtons[i]);
        }
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

//        if(questionNumber == 1) {
//            buttonsContainer.getChildren().add(btnNext);
//        }
//        else{
//            var btnPrev = new Button();
//            btnPrev.setText("previous");
//            btnPrev.setOnAction(e -> onPreviousButtonClick());
//
//            buttonsContainer.getChildren().add(btnPrev);
//
//            if(questionNumber == numberOfQuestions){
//                var btnFinish = new Button();
//                btnFinish.setText("finish");
//                btnFinish.setOnAction(e -> onFinishButtonClick());
//
//                buttonsContainer.getChildren().add(btnFinish);
//            }
//            else{
//                buttonsContainer.getChildren().add(btnNext);
//            }
//        }
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

    public SingleChoiceQuestion getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(QuestionWrapper q) {
        this.question = (SingleChoiceQuestion) q.getQuestion();
        this.timeLimit = q.GetTimeLimit();
    }
}
