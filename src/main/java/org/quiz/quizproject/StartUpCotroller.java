package org.quiz.quizproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class StartUpCotroller {
    @FXML
    private Label quizNameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private VBox vbox;

    @FXML
    public void onStartButtonClick(){
        String name = nameTextField.getText().toLowerCase().trim();

        try{
            if(name.length() > 2){
                var app = QuizApplication.getInstance();

                app.setParticipantName(name);

                app.startQuiz();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Name is empty!");
                alert.setHeaderText("Name has to have at least 2 characters excluding spaces");

                alert.showAndWait();
            }
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred:");
            alert.setHeaderText(ex.getMessage());

            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        var quiz = QuizApplication.getInstance().getCurrentQuiz();

        quizNameLabel.setText(quiz.getTitle());

        Label timeLimitLabel = new Label();

        if(quiz.getTimeLimit() > 0){
            timeLimitLabel.setText("This quiz has time limit: " + quiz.getTimeLimit() + " minutes");
        }
        else{
            timeLimitLabel.setText("This quiz has no time limit");
        }

        vbox.getChildren().add(timeLimitLabel);

        Button startQuizButton = new Button();
        startQuizButton.setText("start quiz");
        startQuizButton.setOnAction(e -> onStartButtonClick());
        vbox.getChildren().add(startQuizButton);
    }
}
