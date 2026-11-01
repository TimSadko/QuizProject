package org.quiz.quizproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.quiz.quizproject.controller.QuizController;
import org.quiz.quizproject.controller.ResultsPageController;
import org.quiz.quizproject.models.AttemptResult;
import org.quiz.quizproject.models.QuestionWrapper;
import org.quiz.quizproject.models.Quiz;
import org.quiz.quizproject.models.QuizResult;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class QuizApplication extends Application {
    private Stage mainStage;
    private GameManager manager;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stage.setMinHeight(220);
        stage.setMinWidth(300);

        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/icon.png"))));

        manager = GameManager.getInstance();
        manager.setApp(this);

        Platform.setImplicitExit(false);

        FXMLLoader fxmlLoader = new FXMLLoader(QuizApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());

        stage.setTitle("Quiz App");
        stage.setScene(scene);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(manager.isHasQuizStarted()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Are you sure you want to exit? All of your progress will be lost.");
                    alert.setContentText("Please choose an option.");

                    ButtonType yesButton = new ButtonType("Exit");
                    ButtonType noButton = new ButtonType("Cancel");

                    alert.getButtonTypes().setAll(yesButton, noButton);

                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.isPresent() && result.get() == noButton)
                    {
                        event.consume();
                    }
                }
            }
        });

        stage.show();
    }

    public void beginQuiz(){
        try {
            replaceSceneContent("start-up.fxml");
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred:");
            alert.setContentText("Failed to start quiz.");
            //alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }

    public void replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(Objects.requireNonNull(QuizApplication.class.getResource(fxml)), null, new JavaFXBuilderFactory());
        Scene scene = mainStage.getScene();

        if (scene == null) {
            scene = new Scene(page, 960, 540);
            //scene.getStylesheets().add(QuizApplication.class.getResource("demo.css").toExternalForm());
            mainStage.setScene(scene);
        }
        else {
            mainStage.getScene().setRoot(page);
        }

        mainStage.sizeToScene();
    }

    public void moveToQuestion(QuestionWrapper cur, int questionNumber) {
        try{
            switch (cur.getQuestion().getType()){
                case TrueFalse:
                    moveToQuestion("quiz-true-false.fxml" ,cur, questionNumber);
                    break;
                case SingleChoice:
                    moveToQuestion("quiz-single-choice.fxml" ,cur, questionNumber);
                    break;
                case MultipleChoice:
                    moveToQuestion("quiz-multiple-choice.fxml" ,cur, questionNumber);
                    break;
                case Text:
                    moveToQuestion("quiz-text.fxml" ,cur, questionNumber);
                    break;
                default:
                    throw new IOException("Unknown question type, possibly faulty quiz file.");
            }
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred:");
            alert.setContentText("Unknown question type, possibly faulty quiz file.");
            //alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }

    private void moveToQuestion(String fxml, QuestionWrapper q, int questionNumber) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent page = (Parent)loader.load();

        QuizController controller = loader.getController();

        controller.setQuestion(q);
        controller.setNumberOfQuestions(manager.getCurrentQuiz().getPages().size());
        controller.setQuestionNumber(questionNumber);

        controller.init();

        Scene scene = mainStage.getScene();
        mainStage.getScene().setRoot(page);
        mainStage.sizeToScene();
    }

    public void moveToMainMenu() {
        try{
            replaceSceneContent("main-menu.fxml");
        }
        catch (Exception ignored){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred:");
            alert.setContentText("Failed to start quiz.");
            //alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }

    public void finishQuiz() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("results-page.fxml"));
            Parent page = (Parent) loader.load();

            ResultsPageController controller = loader.getController();

            controller.init();

            Scene scene = mainStage.getScene();
            mainStage.getScene().setRoot(page);
            mainStage.sizeToScene();
        }
        catch (Exception ex){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("An error has occurred:");
            err.setHeaderText(ex.getMessage());
            err.setContentText("Failed to finish quiz.");

            err.showAndWait();
        }
    }

    public Stage getMainStage() {
        return mainStage;
    }
}
