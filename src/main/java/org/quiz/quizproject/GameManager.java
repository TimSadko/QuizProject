package org.quiz.quizproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.quiz.quizproject.controller.ResultsPageController;
import org.quiz.quizproject.models.AttemptResult;
import org.quiz.quizproject.models.QuestionWrapper;
import org.quiz.quizproject.models.Quiz;
import org.quiz.quizproject.models.QuizResult;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameManager {
    private static GameManager instance;

    private GameManager(){}

    public static GameManager getInstance(){
        if(instance == null) instance = new GameManager();

        return instance;
    }

    private QuizApplication app;

    private Quiz currentQuiz;
    private String participantName;

    private String outputPath;

    private boolean hasQuizStarted = false;

    private ArrayList<AttemptResult> attempts;

    public void finishQuiz(){
        saveQuizAttempt();

        hasQuizStarted = false;

        app.finishQuiz();
    }

    private void saveQuizAttempt() {
        try {
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
        catch (Exception ex){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("An error has occurred:");
            err.setContentText("Failed to save quiz.");

            err.showAndWait();
        }
    }

    private void printToFile(File f, String data) throws IOException {
        PrintWriter writer = new PrintWriter(f, StandardCharsets.UTF_8);
        writer.print(data);
        writer.close();
    }

    public void moveToFirstQuestion()
    {
        hasQuizStarted = true;

        moveToQuestion(1);
    }


    public void startQuiz(){
        app.beginQuiz();
    }

    public void moveToMainMenu(){
        app.moveToMainMenu();
    }

    public void moveToQuestion(int questionNumber){
        QuestionWrapper cur = currentQuiz.getPages().get(questionNumber - 1);

        app.moveToQuestion(cur, questionNumber);
    }

    public void saveResultsSCV()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select where to save the file");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        chooser.getExtensionFilters().add(extFilter);

        File f = chooser.showSaveDialog(app.getMainStage());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f.getPath()), StandardCharsets.UTF_8));
            for (AttemptResult attempt : getAttempts())
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(currentQuiz.getQuizId());
                oneLine.append(";");
                oneLine.append(currentQuiz.getTitle());
                oneLine.append(";");
                oneLine.append(attempt.getPlayerName());
                oneLine.append(";");
                oneLine.append(attempt.getCorrectQuestions());
                oneLine.append(";");
                oneLine.append(attempt.getTotalQuestions());
                oneLine.append(";");
                oneLine.append(formatter.format(attempt.getDate()));
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception ex){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("An error has occurred:");
            err.setContentText("Failed to export scoreboard!");

            err.showAndWait();
        }
    }

    public QuizApplication getApp() {
        return app;
    }

    public void setApp(QuizApplication app) {
        this.app = app;
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public boolean isHasQuizStarted() {
        return hasQuizStarted;
    }

    public void setHasQuizStarted(boolean hasQuizStarted) {
        this.hasQuizStarted = hasQuizStarted;
    }

    public ArrayList<AttemptResult> getAttempts() {
        return attempts;
    }

    public void setAttempts(ArrayList<AttemptResult> attempts) {
        this.attempts = attempts;
    }
}
