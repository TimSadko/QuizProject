package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class QuizTrueFalseController implements QuizController {
    private boolean mustSelect;
    private int numberOfQuestions;
    private int questionNumber;

    private TrueFalseQuestion question;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Label questionTextLabel;

    @FXML
    private HBox buttonsContainer;

    @FXML
    private RadioButton radioTrue;

    @FXML
    private RadioButton radioFalse;

    private ToggleGroup tfGroup;

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
        var rb = (RadioButton)tfGroup.getSelectedToggle();

        if(rb == null){
            if(mustSelect && !noMessage){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("An answer must be selected!");

                alert.show();

                return false;
            }

            return true;
        }

        question.setIsAnswerSelected(true);

        question.setSelectedAnswer(rb == radioTrue);

        return true;
    }

    @Override
    public void init(){
        initButtons();

        questionNumberLabel.setText(questionNumber + "/" + numberOfQuestions);
        questionTextLabel.setText(question.getText());

        tfGroup = new ToggleGroup();

        radioFalse.setToggleGroup(tfGroup);
        radioTrue.setToggleGroup(tfGroup);

        if(question.getIsAnswerSelected()){
            if(question.getSelectedAnswer()) radioTrue.setSelected(true);
            else radioFalse.setSelected(true);
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

    public TrueFalseQuestion getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question question) {
        this.question = (TrueFalseQuestion) question;
    }
}
