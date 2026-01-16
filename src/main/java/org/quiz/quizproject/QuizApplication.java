package org.quiz.quizproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

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

    private ArrayList<Question> questions;

    private Timer timer;
    private long timerStart;
    private long timerStop;

    private boolean practice;

    private ArrayList<AttemptResult> attemts;

    private static QuizApplication instance;

    public static QuizApplication getInstance() {
        return instance;
    }

    public boolean isPractice() {
        return practice;
    }

    public void setPractice(boolean practice) {
        this.practice = practice;
    }

    public ArrayList<AttemptResult> getAttemts() {
        return attemts;
    }

    public long getTimerStart() {
        return timerStart;
    }

    public void setTimerStart(long timerStart) {
        this.timerStart = timerStart;
    }

    public long getTimerStop() {
        return timerStop;
    }

    public void setTimerStop(long timerStop) {
        this.timerStop = timerStop;
    }

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

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        instance = this;

        FXMLLoader fxmlLoader = new FXMLLoader(QuizApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);

        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("Quiz App");
        stage.setScene(scene);
        stage.show();
    }

    private void finishQuizTimer() throws IOException {
        timerStop = System.currentTimeMillis();

        saveQuizAttempt();

        Platform.runLater(new Runnable() {
            // Because this code is ran in different thread, in order for it to work, all UI changes has to be this way
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("results-page.fxml"));
                Parent page = null;

                try {
                    page = (Parent)loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ResultsPageController controller = loader.getController();

                controller.init();

                Scene scene = mainStage.getScene();
                mainStage.getScene().setRoot(page);
                mainStage.sizeToScene();
            }
        });
    }

    public void finishQuiz(){
        if(timer != null) {
            timer.cancel(); // Stop timer
        }

        timerStop = System.currentTimeMillis();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quiz Ending:");
        alert.setHeaderText("Are you sure you want to end the quiz?");

        Optional<ButtonType> buttonType = alert.showAndWait();

        if(!buttonType.isPresent() || !buttonType.get().equals(ButtonType.OK)) return;

        try {
            if(!practice) saveQuizAttempt();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("results-page.fxml"));
            Parent page = (Parent)loader.load();

            ResultsPageController controller = loader.getController();

            controller.init();

            Scene scene = mainStage.getScene();
            mainStage.getScene().setRoot(page);
            mainStage.sizeToScene();
        }
        catch (Exception ex){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("An error has occurred:");
            //err.setHeaderText(ex.getMessage());
            err.setContentText(ex.getMessage());

            err.showAndWait();
        }
    }

    private void saveQuizAttempt() throws IOException {
        File f = new File(outputPath, currentQuiz.getId() + "-results.json");

        if(f.exists() && !f.isDirectory()) { // if file already exists read and deserialize it, then add new attempt and save all to file
            String oldJson = Files.readString(f.toPath());

            ObjectMapper objectMapper = new ObjectMapper();

            QuizResult oldQuizResult = objectMapper.readValue(oldJson, QuizResult.class);

            AttemptResult newAttempt = new AttemptResult();

            newAttempt.setPlayerName(participantName);
            newAttempt.setDate(new Date());
            newAttempt.setPoints(calcPoints(currentQuiz));

            oldQuizResult.getResults().add(newAttempt);

            attemts = oldQuizResult.getResults();

            PrintWriter writer = new PrintWriter(f, StandardCharsets.UTF_8);
            writer.print(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(oldQuizResult));
            writer.close();
        }
        else { // if there is no file create new QuizResult, fill it in, and save it
            QuizResult quizResult = new QuizResult();

            quizResult.setQuizId(currentQuiz.getId());
            quizResult.setName(currentQuiz.getTitle());
            quizResult.setMaxPoints(calcQuizMaxPoints(currentQuiz));
            quizResult.setResults(new ArrayList<AttemptResult>());

            AttemptResult newAttempt = new AttemptResult();

            newAttempt.setPlayerName(participantName);
            newAttempt.setDate(new Date());
            newAttempt.setPoints(calcPoints(currentQuiz));

            quizResult.getResults().add(newAttempt);

            attemts = quizResult.getResults();

            PrintWriter writer = new PrintWriter(f, StandardCharsets.UTF_8);
            writer.print(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(quizResult));
            writer.close();
        }
    }

    public int calcQuizMaxPoints(Quiz quiz) {
        int maxPoints = 0;

        for (int i = 0; i < quiz.getQuestions().length; i++){
            maxPoints += quiz.getQuestions()[i].getPoints();
        }

        return maxPoints;
    }

    public int calcPoints(Quiz quiz) {
        int points = 0;

        for (int i = 0; i < quiz.getQuestions().length; i++){
            points += quiz.getQuestions()[i].calcPoints();
        }

        return points;
    }

    public void startQuiz(){
        questions = new ArrayList<>(Arrays.asList(currentQuiz.getQuestions()));

        if(currentQuiz.isMixQuestions()) Collections.shuffle(questions);

        if(currentQuiz.getTimeLimit() > 0){ // Set timer
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        finishQuizTimer();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, ((long)currentQuiz.getTimeLimit() * 60 * 1000));
        }

        moveToQuestion(1);

        timerStart = System.currentTimeMillis();
    }

    public void replaceSceneContent(String fxml) throws Exception {
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
    }

    public void moveToQuestion(int questionNumber){
        Question cur = questions.get(questionNumber - 1);

        try{
            switch (cur.getType()){
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
                    throw new IOException("Unknown question type, possibly faulty quiz file");
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

    private void moveToQuestion(String fxml, Question q, int questionNumber) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent page = (Parent)loader.load();

        QuizController controller = loader.getController();

        controller.setQuestion(q);
        controller.setMustSelect(currentQuiz.isAnswerRequired());
        controller.setNumberOfQuestions(questions.size());
        controller.setQuestionNumber(questionNumber);

        controller.init();

        Scene scene = mainStage.getScene();
        mainStage.getScene().setRoot(page);
        mainStage.sizeToScene();
    }
}
