package org.quiz.quizproject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainMenuController {
    @FXML
    private Button loadQuizButton;

    @FXML
    protected void OnLoadQuizButtonClick() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Quiz File");
        File f = chooser.showOpenDialog(loadQuizButton.getScene().getWindow());

        try{
            String json = Files.readString(f.toPath());

            ObjectMapper objectMapper = new ObjectMapper();
            // Question have a field type, it will try to set it, because it is final it will fail, this config is to prevent it from failing
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

            Quiz quiz = objectMapper.readValue(json, Quiz.class);

            var app = QuizApplication.getInstance();

            app.setCurrentQuiz(quiz);
            app.replaceSceneContent("start-up.fxml");
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An error has occurred:");
            alert.setHeaderText(ex.getMessage());

            alert.showAndWait();
        }
    }

    @FXML
    protected void OnExitButtonClick() {
        Platform.exit();
    }
}
