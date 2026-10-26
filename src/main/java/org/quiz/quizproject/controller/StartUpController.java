package org.quiz.quizproject.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.quiz.quizproject.QuizApplication;

public class StartUpController {
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
            if(name.length() > 1){
                var app = QuizApplication.getInstance();

                app.setParticipantName(name);

                app.startQuiz();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Name is empty!");
                alert.setHeaderText("Name has to have at least 1 characters excluding spaces");

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

        VBox.setMargin(timeLimitLabel, new Insets(0, 0, 10, 0));

        vbox.getChildren().add(timeLimitLabel);

        Button startQuizButton = new Button();
        startQuizButton.setText("start quiz");
        startQuizButton.setOnAction(e -> onStartButtonClick());
        vbox.getChildren().add(startQuizButton);
    }
}
