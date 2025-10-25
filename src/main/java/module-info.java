module org.quiz.quizproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;


    opens org.quiz.quizproject to javafx.fxml;
    exports org.quiz.quizproject;
}