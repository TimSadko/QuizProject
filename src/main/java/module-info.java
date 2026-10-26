module org.quiz.quizproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;
    requires javafx.base;


    opens org.quiz.quizproject to javafx.fxml;
    exports org.quiz.quizproject;
    exports org.quiz.quizproject.questions;
    opens org.quiz.quizproject.questions to javafx.fxml;
    exports org.quiz.quizproject.models;
    opens org.quiz.quizproject.models to javafx.fxml;
    exports org.quiz.quizproject.controller;
    opens org.quiz.quizproject.controller to javafx.fxml;
}