package org.quiz.quizproject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ResultsPageController {
    @FXML
    private VBox infoBox;

    public void init(){
        var app = QuizApplication.getInstance();
        var quiz = app.getCurrentQuiz();

        Label quizNameLabel = new Label();
        quizNameLabel.setText("Quiz name: " + quiz.getTitle());

        VBox.setMargin(quizNameLabel, new Insets(0, 0, 8, 0));

        infoBox.getChildren().add(quizNameLabel);

        if(quiz.isShowResults()){
            Label scoreLabel = new Label();

            int scoredPoints = app.calcPoints(quiz);
            int maxPoints = quiz.getPages().size();
            double percentScore = Math.round((float)scoredPoints / maxPoints * 100.0);

            scoreLabel.setText("Your score is " + scoredPoints + "/" + maxPoints + ", = ~" + percentScore + "%");

            VBox.setMargin(scoreLabel, new Insets(0, 0, 2, 0));

            infoBox.getChildren().add(scoreLabel);

//            if(quiz.getMinScore() > 0){
//                Label passingMessageLabel = new Label();
//
//                if(quiz.getMinScore() <= scoredPoints){
//                    passingMessageLabel.setText("You have passed)))");
//                }
//                else{
//                    passingMessageLabel.setText("You have failed(((");
//                }
//
//                VBox.setMargin(passingMessageLabel, new Insets(0, 0, 8, 0));
//
//                infoBox.getChildren().add(passingMessageLabel);
//            }

//            Date date = new Date(app.getTimerStop() - app.getTimerStart() - 3600000);
//            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//            String dateFormatted = formatter.format(date);
//
//            Label labelTimePassed = new Label();
//            labelTimePassed.setText("Quiz time: " + dateFormatted);

            //VBox.setMargin(labelTimePassed, new Insets(0, 0, 8, 0));

            //infoBox.getChildren().add(labelTimePassed);

            if(!app.isPractice()) {
                app.getAttemts().sort(new Comparator<AttemptResult>() {
                    @Override
                    public int compare(AttemptResult o1, AttemptResult o2) {
                        return Integer.compare(o2.getPoints(), o1.getPoints());
                    }
                });

                AttemptResult cur;

                for (int i = 0; i < app.getAttemts().size(); i++) {
                    cur = app.getAttemts().get(i);
                    cur.propertyScorePercentProperty().set(Integer.toString(cur.getPoints()));
                }

                ObservableList<AttemptResult> observableAttemptsList = FXCollections.observableList(app.getAttemts());

                TableView scoreBoard = new TableView<>();

                VBox.setMargin(scoreBoard, new Insets(0, 0, 8, 0));

                infoBox.getChildren().add(scoreBoard);

                scoreBoard.getStyleClass().add("table-view");

                scoreBoard.setItems(observableAttemptsList);

                TableColumn columnName = new TableColumn("Name");
                columnName.setCellValueFactory(new PropertyValueFactory<AttemptResult, String>("propertyName"));

                TableColumn columnScore = new TableColumn("Score");
                columnScore.setCellValueFactory(new PropertyValueFactory<AttemptResult, String>("propertyScorePercent"));

                TableColumn columnDate = new TableColumn("Date");
                columnDate.setCellValueFactory(new PropertyValueFactory<AttemptResult, String>("propertyDate"));

                scoreBoard.getColumns().addAll(columnName, columnScore, columnDate);



                Button btnSaveCSV = new Button();
                btnSaveCSV.setText("save score table");
                btnSaveCSV.setOnAction(e -> saveSCV());

                VBox.setMargin(btnSaveCSV, new Insets(0, 0, 8, 0));

                infoBox.getChildren().add(btnSaveCSV);
            }

            Button btnQuit = new Button();
            btnQuit.setText("quit");
            btnQuit.setOnAction(e -> onQuitButtonClick());

            VBox.setMargin(btnQuit, new Insets(0, 0, 8, 0));

            infoBox.getChildren().add(btnQuit);
        }
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
            for (AttemptResult attempt : app.getAttemts())
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(quiz.getQuizId());
                oneLine.append(";");
                oneLine.append(quiz.getTitle());
                oneLine.append(";");
                oneLine.append(attempt.getPlayerName());
                oneLine.append(";");
                oneLine.append(attempt.getPoints());
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
            err.setContentText(ex.getMessage());

            err.showAndWait();
        }
    }

    private void onQuitButtonClick(){
        Platform.exit();
    }
}
