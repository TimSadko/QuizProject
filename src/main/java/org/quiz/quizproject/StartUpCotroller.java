package org.quiz.quizproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;

public class StartUpCotroller {
    @FXML
    private Label quizNameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    public void OnStartButtonClick(){
        String name = nameTextField.getText().toLowerCase().trim();

        try{
            if(name.length() > 2){
                var app = QuizApplication.getInstance();

                app.setParticipantName(name);
                app.replaceSceneContent("main-quiz.fxml");
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Name is empty!");
                alert.setHeaderText("Name has to have at least 2 characters excluding spaces");

                alert.showAndWait();
            }
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An error has occurred:");
            alert.setHeaderText(ex.getMessage());

            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        var quiz = QuizApplication.getInstance().getCurrentQuiz();

        quizNameLabel.setText(quiz.getTitle());
    }
}
