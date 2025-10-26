package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class QuizSingleChoiceController implements QuizController {
    private boolean mustSelect;
    private int numberOfQuestions;
    private int questionNumber;

    private SingleChoiceQuestion question;

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
            if(mustSelect && !noMessage){
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

        radioButtons = new RadioButton[question.getAnswers().length];

        for(int i = 0; i < radioButtons.length; i++){
            radioButtons[i] = new RadioButton();
            radioButtons[i].setText(question.getAnswers()[i]);
            radioButtons[i].setToggleGroup(scGroup);

            if(question.getIsAnswerSelected() && Objects.equals(radioButtons[i].getText(), question.getSelectedAnswer())) radioButtons[i].setSelected(true);

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

    public SingleChoiceQuestion getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question question) {
        this.question = (SingleChoiceQuestion)question;
    }
}
