package org.quiz.quizproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultsPageController {
    @FXML
    private VBox infoBox;

    public void init(){
        var app = QuizApplication.getInstance();
        var quiz = app.getCurrentQuiz();

        if(quiz.isShowResults()){
            Label scoreLabel = new Label();

            int scoredPoints = app.calcPoints(quiz);
            int maxPoints = app.calcQuizMaxPoints(quiz);
            double percentScore = Math.round((float)scoredPoints / maxPoints * 100.0);

            scoreLabel.setText("Your score is " + scoredPoints + "/" + maxPoints + ", = ~" + percentScore + "%");

            infoBox.getChildren().add(scoreLabel);

            if(quiz.getMinScore() > 0){
                Label passingMessageLabel = new Label();

                if(quiz.getMinScore() <= scoredPoints){
                    passingMessageLabel.setText("You have passed)))");
                }
                else{
                    passingMessageLabel.setText("You have failed(((");
                }

                infoBox.getChildren().add(passingMessageLabel);
            }

            Date date = new Date(app.getTimerStop() - app.getTimerStart() - 3600000);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String dateFormatted = formatter.format(date);

            Label labelTimePassed = new Label();
            labelTimePassed.setText("Quiz time: " + dateFormatted);
            infoBox.getChildren().add(labelTimePassed);

            Button btnQuit = new Button();
            btnQuit.setText("quit");
            btnQuit.setOnAction(e -> onQuitButtonClick());

            infoBox.getChildren().add(btnQuit);
        }
    }

    private void onQuitButtonClick(){
        Platform.exit();
    }
}
