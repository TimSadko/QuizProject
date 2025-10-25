package org.quiz.quizproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainQuizController {
    private ObservableList<Question> questions;

    public ObservableList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ObservableList<Question> questions) {
        this.questions = questions;
    }

    public void initialize() {
        questions = FXCollections.observableArrayList(QuizApplication.getInstance().getCurrentQuiz().getQuestions());
    }
}
