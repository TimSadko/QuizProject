package org.quiz.quizproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class QuizApplication extends Application {
    private Stage mainStage;
    private Quiz currentQuiz;
    private String participantName;

    private ArrayList<Question> questions;

    private static QuizApplication instance;

    public static QuizApplication getInstance() {
        return instance;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        instance = this;

        FXMLLoader fxmlLoader = new FXMLLoader(QuizApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        stage.setTitle("Quiz App");
        stage.setScene(scene);
        stage.show();
    }

    public void moveToQuestion(int questionNumber){
        Question cur = questions.get(questionNumber - 1);

        try{
            switch (cur.getType()){
                case TrueFalse:
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz-true-false.fxml"));
                    Parent page = (Parent)loader.load();

                    QuizTrueFalseController controller = loader.getController();

                    controller.setQuestion((TrueFalseQuestion) cur);
                    controller.setMustSelect(currentQuiz.isAnswerRequired());
                    controller.setNumberOfQuestions(questions.size());
                    controller.setQuestionNumber(questionNumber);

                    controller.init();

                    Scene scene = mainStage.getScene();
                    mainStage.getScene().setRoot(page);
                    mainStage.sizeToScene();
                    break;
            }
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred:");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }

    public void finishQuiz(){

    }

    public void startQuiz(){
        questions = new ArrayList(Arrays.asList(currentQuiz.getQuestions()));

        if(currentQuiz.isMixQuestions()) Collections.shuffle(questions);

        moveToQuestion(1);
    }

    public Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(QuizApplication.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = mainStage.getScene();
        if (scene == null) {
            scene = new Scene(page, 960, 540);
            //scene.getStylesheets().add(QuizApplication.class.getResource("demo.css").toExternalForm());
            mainStage.setScene(scene);
        } else {
            mainStage.getScene().setRoot(page);
        }
        mainStage.sizeToScene();
        return page;
    }
}
