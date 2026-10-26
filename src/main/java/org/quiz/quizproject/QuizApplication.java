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
import java.util.*;

public class QuizApplication extends Application {
    private Stage mainStage;
    private Quiz currentQuiz;
    private String participantName;

    private String outputPath;

    private boolean hasQuizStarted = false;

//    private Timer timer;
//    private long timerStart;
//    private long timerStop;

    private ArrayList<AttemptResult> attempts;

    private static QuizApplication instance;

    public static QuizApplication getInstance() {
        return instance;
    }

//    public long getTimerStart() {
//        return timerStart;
//    }
//
//    public void setTimerStart(long timerStart) {
//        this.timerStart = timerStart;
//    }
//
//    public long getTimerStop() {
//        return timerStop;
//    }
//
//    public void setTimerStop(long timerStop) {
//        this.timerStop = timerStop;
//    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
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

    public ArrayList<AttemptResult> getAttempts() {
        return attempts;
    }

    public void setAttempts(ArrayList<AttemptResult> attempts) {
        this.attempts = attempts;
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stage.setMinHeight(220);
        stage.setMinWidth(300);

        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/icon.png"))));

        instance = this;

        Platform.setImplicitExit(false);

        FXMLLoader fxmlLoader = new FXMLLoader(QuizApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());

        stage.setTitle("Quiz App");
        stage.setScene(scene);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(hasQuizStarted){
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

    public void finishQuiz(){
        try {
            saveQuizAttempt();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("results-page.fxml"));
            Parent page = (Parent)loader.load();

            ResultsPageController controller = loader.getController();

            controller.init();

            hasQuizStarted = false;

            Scene scene = mainStage.getScene();
            mainStage.getScene().setRoot(page);
            mainStage.sizeToScene();
        }
        catch (Exception ex){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("An error has occurred:");
            err.setHeaderText(ex.getMessage());
            err.setContentText("Failed to save quiz.");

            err.showAndWait();
        }
    }

    private void saveQuizAttempt() throws IOException {
        File f = new File(outputPath, currentQuiz.getQuizId() + "-results.json");

        AttemptResult newAttempt = new AttemptResult();

        newAttempt.setPlayerName(participantName);
        newAttempt.setDate(new Date());
        newAttempt.setTotalQuestions(currentQuiz.getPages().size());
        newAttempt.setCorrectQuestions(currentQuiz.countCorrectQuestions());

        if(f.exists() && !f.isDirectory()) { // if file already exists, then read and deserialize it, and add new attempt and save all to file.
            String oldJson = Files.readString(f.toPath());

            ObjectMapper objectMapper = new ObjectMapper();

            QuizResult oldQuizResults = objectMapper.readValue(oldJson, QuizResult.class);

            oldQuizResults.getResults().add(newAttempt);

            attempts = oldQuizResults.getResults();

            printToFile(f, objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(oldQuizResults));
        }
        else { // if there is no file, then create new QuizResult, and fill it in, and save it.
            QuizResult quizResult = new QuizResult();

            quizResult.setQuizId(currentQuiz.getQuizId());
            quizResult.setName(currentQuiz.getTitle());
            quizResult.setResults(new ArrayList<AttemptResult>());

            quizResult.getResults().add(newAttempt);

            attempts = quizResult.getResults();

            printToFile(f, new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(quizResult));
        }
    }

    private void printToFile(File f, String data) throws IOException {
        PrintWriter writer = new PrintWriter(f, StandardCharsets.UTF_8);
        writer.print(data);
        writer.close();
    }

    public void startQuiz(){
        hasQuizStarted = true;

        moveToQuestion(1);
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

    public void moveToQuestion(int questionNumber){
        QuestionWrapper cur = currentQuiz.getPages().get(questionNumber - 1);

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

            alert.showAndWait();
        }
    }

    private void moveToQuestion(String fxml, QuestionWrapper q, int questionNumber) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent page = (Parent)loader.load();

        QuizController controller = loader.getController();

        controller.setQuestion(q);
        controller.setNumberOfQuestions(currentQuiz.getPages().size());
        controller.setQuestionNumber(questionNumber);

        controller.init();

        Scene scene = mainStage.getScene();
        mainStage.getScene().setRoot(page);
        mainStage.sizeToScene();
    }

    public void moveToMainMenu() throws Exception {
        replaceSceneContent("main-menu.fxml");
    }
}
