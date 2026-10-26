package org.quiz.quizproject.controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.quiz.quizproject.QuizApplication;
import org.quiz.quizproject.models.AttemptResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class ResultsPageController {
    @FXML
    private VBox infoBox;

    @FXML
    private Label quizNameLabel;

    @FXML
    private Label quizScoreLabel;

    @FXML
    private TableView<AttemptResult> scoreBoard;

    @FXML
    protected void onQuitButtonClick() {
        quit();
    }

    @FXML
    protected void onExportCSVButtonClick() {
        saveSCV();
    }

    @FXML
    protected void onQuizAgainButtonClick() {
        quizAgain();
    }

    public void init(){
        var app = QuizApplication.getInstance();
        var quiz = app.getCurrentQuiz();

        quizNameLabel.setText("Quiz name: " + quiz.getTitle());

        // Parse text for score label
        int correctQuestions = quiz.countCorrectQuestions();
        int TotalQuestion = quiz.getPages().size();
        double percentScore = Math.round((float)correctQuestions / TotalQuestion * 100.0);

        quizScoreLabel.setText("Your score is " + correctQuestions + "/" + TotalQuestion + ", = ~" + percentScore + "%");

        // Sort and set attempts for score table.
        var attempts = app.getAttempts();
        attempts.sort(new Comparator<AttemptResult>() {
            @Override
            public int compare(AttemptResult o1, AttemptResult o2) {
                return Integer.compare(o2.getCorrectQuestions(), o1.getCorrectQuestions());
            }
        });
        ObservableList<AttemptResult> observableAttemptsList = FXCollections.observableList(attempts);

        scoreBoard.setItems(observableAttemptsList);

        // Add columns to the table
        var columnName = new TableColumn<AttemptResult, String>("Name");
        columnName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPlayerName()));
        scoreBoard.getColumns().add(columnName);

        var columnScore = new TableColumn<AttemptResult, String>("Score");
        columnScore.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCorrectQuestions() + ""));
        scoreBoard.getColumns().add(columnScore);

        var columnDate = new TableColumn<AttemptResult, String>("Date");
        columnDate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDateString()));
        scoreBoard.getColumns().add(columnDate);
    }

    private void saveSCV()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select where to save the file");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        chooser.getExtensionFilters().add(extFilter);

        File f = chooser.showSaveDialog(infoBox.getScene().getWindow());

        var app = QuizApplication.getInstance();
        var quiz = app.getCurrentQuiz();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f.getPath()), "UTF-8"));
            for (AttemptResult attempt : app.getAttempts())
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(quiz.getQuizId());
                oneLine.append(";");
                oneLine.append(quiz.getTitle());
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

    private void quit(){
        Platform.exit();
    }

    private void quizAgain(){
        var app = QuizApplication.getInstance();

        try {
            app.moveToMainMenu();
        } catch (Exception e) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("An error has occurred:");
            err.setContentText("Failed to quiz again!");

            err.showAndWait();
        }
    }
}
