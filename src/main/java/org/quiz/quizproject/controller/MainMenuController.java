package org.quiz.quizproject.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.quiz.quizproject.QuizApplication;
import org.quiz.quizproject.models.Quiz;

import java.io.File;
import java.nio.file.Files;

public class MainMenuController {
    @FXML
    private Button loadQuizButton;

    @FXML
    protected void onLoadQuizButtonClick() {
        loadQuiz();
    }

    private void loadQuiz()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Quiz File");
        File f = chooser.showOpenDialog(loadQuizButton.getScene().getWindow());

        if(f == null || !f.exists()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid quiz file");
            alert.setHeaderText("A quiz file must be chosen before the start of a quiz");

            alert.showAndWait();

            return;
        }

        try{
            String json = Files.readString(f.toPath());

            ObjectMapper objectMapper = new ObjectMapper();
            // Question have a field type, it will try to set it, because it is final it will fail, this config is to prevent it from failing
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Quiz quiz = objectMapper.readValue(json, Quiz.class);

            if(quiz.getPages() == null || quiz.getPages().isEmpty()) throw new ClassCastException();

            var app = QuizApplication.getInstance();

            app.setOutputPath(f.getParent());
            app.setCurrentQuiz(quiz);
            app.replaceSceneContent("start-up.fxml");
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred:");
            alert.setHeaderText("Failed to read selected quiz!");
            alert.setContentText("Selected quiz uses unsupported format, or is corrupted. Make sure you have selected the correct quiz.");

            alert.showAndWait();
        }
    }


    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }
}
