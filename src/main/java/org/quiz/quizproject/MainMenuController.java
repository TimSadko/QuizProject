package org.quiz.quizproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void OnStartNewQuizButtonClick() {

    }

    @FXML
    protected void OnExitButtonClick() {
        Platform.exit();
    }
}
