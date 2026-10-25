package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuizMultipleChoiceController implements QuizController {
    private int numberOfQuestions;
    private int questionNumber;

    private MultipleChoiceQuestion question;

    private int timeLimit;

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

        if(!atLeastOneSelected && question.isRequired() && !noMessage){
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
        questionTextLabel.setText(question.getTitle() + " (Multiple answers may be chosen)");

        radioButtons = new RadioButton[question.getChoices().length];

        for(int i = 0; i < radioButtons.length; i++){
            radioButtons[i] = new RadioButton();
            radioButtons[i].setText(question.getChoices()[i]);

            if(question.getIsAnswerSelected() && question.getSelectedAnswers().contains(radioButtons[i].getText())) radioButtons[i].setSelected(true);

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
    public void setQuestion(QuestionWrapper q) {
        this.question = (MultipleChoiceQuestion) q.getQuestion();
        this.timeLimit = q.GetTimeLimit();
    }
}
